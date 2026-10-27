package fr.efrei.transfret;

import fr.efrei.transport.contrat.Canal;
import fr.efrei.transport.contrat.ColisDto;
import fr.efrei.transport.contrat.DemandeClient;
import fr.efrei.transport.contrat.DemandeDevis;
import fr.efrei.transport.contrat.DemandeExpedition;
import fr.efrei.transport.contrat.EtatExpedition;
import fr.efrei.transport.contrat.Notification;
import fr.efrei.transport.contrat.Statut;
import fr.efrei.transport.contrat.SystemeTransport;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class NouveauSystemeAdapter implements SystemeTransport {

    private ClientRepository clientRepository;
    private ExpeditionRepository expeditionRepository;
    private ConsoleEmailService consoleEmailService;
    private ConsoleSmsService consoleSmsService;
    private ServiceClient serviceClient;
    private ServiceExpedition serviceExpedition;

    @Override
    public void reinitialiser() {
        Flotte flotte = new Flotte();
        clientRepository = new MemoireClientRepository();
        expeditionRepository = new MemoireExpeditionRepository();
        consoleEmailService = new ConsoleEmailService();
        consoleSmsService = new ConsoleSmsService();

        Notificateur notificateur = new Notificateur(consoleEmailService, consoleSmsService);
        CalculateurPrix calculateur = new CalculateurPrix();
        AffectationVehicule affectation = new AffectationVehicule(flotte);
        ValidateurExpedition validateurExpedition = new ValidateurExpedition();
        EstimateurDelai estimateurDelai = new EstimateurDelai();

        serviceClient = new ServiceClient(clientRepository, consoleEmailService);
        serviceExpedition = new ServiceExpedition(expeditionRepository, calculateur, affectation, validateurExpedition, estimateurDelai, notificateur);
    }

    @Override
    public Optional<String> creerClient(DemandeClient d) {
        TypeClient typeClient = TypeClient.valueOf(d.type().name());
        Client client = serviceClient.creerClient(d.nom(), d.email(), d.telephone(), d.adresse(), typeClient, d.siret());
        return Optional.ofNullable(client).map(Client::getEmail);
    }

    @Override
    public Optional<String> creerExpedition(DemandeExpedition d) {
        Client client = clientRepository.trouverParEmail(d.refClient()).orElse(null);
        ModeTransport mode = ModeTransport.valueOf(d.mode().name());
        Expedition expedition = serviceExpedition.creerExpedition(client, versColis(d.colis()), d.villeDepart(),
                d.villeArrivee(), mode, d.urgent(), d.assurance());
        return Optional.ofNullable(expedition).map(Expedition::getId);
    }

    @Override
    public Optional<EtatExpedition> consulter(String id) {
        Expedition e = expeditionRepository.trouverParId(id).orElse(null);
        if (e == null) {
            return Optional.empty();
        }
        int delai = (int) ChronoUnit.DAYS.between(jour(e.getDateCreation()), jour(e.getDateLivraisonPrevue()));
        return Optional.of(new EtatExpedition(e.getId(), Statut.valueOf(e.getStatut().name()), e.getPrixHT(), e.getPrixTTC(),
                delai, e.getVehicule().getImmatriculation(), e.getFraisAnnulation()));
    }

    @Override
    public OptionalDouble devisTTC(DemandeDevis d) {
        ModeTransport mode = ModeTransport.valueOf(d.mode().name());
        TypeClient typeClient = TypeClient.valueOf(d.typeClient().name());
        double devis = serviceExpedition.calculerDevis(versColis(d.colis()), d.villeDepart(), d.villeArrivee(),
                mode, d.urgent(), d.assurance(), typeClient);
        return devis == -1 ? OptionalDouble.empty() : OptionalDouble.of(devis);
    }

    @Override
    public boolean demarrer(String id) {
        return serviceExpedition.demarrerExpedition(id);
    }

    @Override
    public boolean livrer(String id) {
        return serviceExpedition.livrerExpedition(id);
    }

    @Override
    public boolean annuler(String id) {
        return serviceExpedition.annulerExpedition(id);
    }

    @Override
    public Optional<String> facture(String id) {
        return expeditionRepository.trouverParId(id).map(Expedition::genererFacture);
    }

    @Override
    public List<Notification> notifications() {
        List<Notification> notifications = new ArrayList<>();
        for (String[] email : consoleEmailService.getJournal()) {
            notifications.add(new Notification(Canal.EMAIL, email[0], email[1]));
        }
        for (String[] sms : consoleSmsService.getJournal()) {
            notifications.add(new Notification(Canal.SMS, sms[0], sms[1]));
        }
        return notifications;
    }

    private static java.time.LocalDate jour(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static List<Colis> versColis(List<ColisDto> colis) {
        return colis.stream().map(NouveauSystemeAdapter::versColis).collect(Collectors.toCollection(ArrayList::new));
    }

    private static Colis versColis(ColisDto c) {
        return new Colis(c.description(), c.poidsKg(), c.longueurCm(), c.largeurCm(), c.hauteurCm(),
                versTypeMarchandise(c.type()), c.valeurDeclaree());
    }

    private static TypeMarchandise versTypeMarchandise(fr.efrei.transport.contrat.TypeMarchandise type) {
        return switch (type) {
            case STANDARD -> TypeMarchandise.STANDARD;
            case FRAGILE -> TypeMarchandise.FRAGILE;
            case DANGEREUX -> TypeMarchandise.DANGEREUX;
            case REFRIGERE -> TypeMarchandise.REFRIGERE;
        };
    }
}
