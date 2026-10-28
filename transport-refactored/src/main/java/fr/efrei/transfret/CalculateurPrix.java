package fr.efrei.transfret;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CalculateurPrix {

    private final AssembleurCalculateurPrix assembleur;

    public CalculateurPrix() {
        this(creerTarifsParDefaut(), creerRemisesParDefaut());
    }

    public CalculateurPrix(Map<ModeTransport, TarifTransport> tarifs, Map<TypeClient, PolitiqueRemise> remises) {
        this(new AssembleurCalculateurPrix(tarifs, remises));
    }

    public CalculateurPrix(AssembleurCalculateurPrix assembleur) {
        this.assembleur = assembleur;
    }

    public static Map<ModeTransport, TarifTransport> creerTarifsParDefaut() {
        Map<ModeTransport, TarifTransport> map = new EnumMap<>(ModeTransport.class);
        map.put(ModeTransport.ROUTE, new TarifRoute());
        map.put(ModeTransport.RAIL, new TarifRail());
        map.put(ModeTransport.MER, new TarifMer());
        map.put(ModeTransport.AIR, new TarifAir());
        return map;
    }

    public static Map<TypeClient, PolitiqueRemise> creerRemisesParDefaut() {
        Map<TypeClient, PolitiqueRemise> map = new EnumMap<>(TypeClient.class);
        map.put(TypeClient.PARTICULIER, new RemiseParticulier());
        map.put(TypeClient.ENTREPRISE, new RemiseEntreprise());
        map.put(TypeClient.PREMIUM, new RemisePremium());
        return map;
    }

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        ContexteCalculPrix contexte = new ContexteCalculPrix(colis, distance, mode, urgent, assurance, typeClient);
        CalculPrix chaine = assembleur.assembler(contexte);
        double total = chaine.calculer(contexte);
        return Utils.arrondir(total);
    }

    public double calculerPrixHT(List<Colis> colis, int distance, String mode, boolean urgent, boolean assurance, String typeClient) {
        return calculerPrixHT(colis, distance, ModeTransport.depuisNom(mode), urgent, assurance, TypeClient.depuisNom(typeClient));
    }
}
