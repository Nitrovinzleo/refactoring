package fr.efrei.transfret;

public class ServiceClient {

    private final ClientRepository clientRepository;
    private final ServiceEmail serviceEmail;
    private final ValidateurClient validateur;

    public ServiceClient(ClientRepository clientRepository, ServiceEmail serviceEmail, ValidateurClient validateur) {
        this.clientRepository = clientRepository;
        this.serviceEmail = serviceEmail;
        this.validateur = validateur;
    }

    public ServiceClient(ClientRepository clientRepository, ServiceEmail serviceEmail) {
        this(clientRepository, serviceEmail, new ValidateurClient());
    }

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        if (!validateur.valider(nom, email, type, siret)) {
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        clientRepository.sauvegarder(c);
        serviceEmail.envoyer(c.getEmail(), "Bienvenue chez TransFret",
                "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.");
        return c;
    }

    public Client creerClient(String nom, String email, String telephone, String adresse, String typeStr, String siret) {
        TypeClient type = TypeClient.depuisNom(typeStr);
        return creerClient(nom, email, telephone, adresse, type, siret);
    }
}
