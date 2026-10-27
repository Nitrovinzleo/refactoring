package fr.efrei.transfret;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CalculateurPrix {

    public static final double COEFF_FRAGILE = 1.15;
    public static final double COEFF_DANGEREUX = 1.30;
    public static final double COEFF_REFRIGERE = 1.20;
    public static final double COEFF_URGENCE = 1.50;

    public static final double TAUX_ASSURANCE = 0.02;
    public static final double MINIMUM_ASSURANCE = 15.0;

    private final Map<ModeTransport, TarifTransport> tarifs;
    private final Map<TypeClient, PolitiqueRemise> remises;

    public CalculateurPrix() {
        this(creerTarifsParDefaut(), creerRemisesParDefaut());
    }

    public CalculateurPrix(Map<ModeTransport, TarifTransport> tarifs, Map<TypeClient, PolitiqueRemise> remises) {
        this.tarifs = tarifs;
        this.remises = remises;
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
        TarifTransport tarif = tarifs.get(mode);
        if (tarif == null) {
            throw new IllegalArgumentException("Mode inconnu : " + mode);
        }
        PolitiqueRemise remise = remises.getOrDefault(typeClient, new RemiseParticulier());

        double prix = tarif.calculerTarifBase(colis, distance);

        boolean fragile = false;
        boolean dangereux = false;
        boolean refrigere = false;
        double valeur = 0;

        for (Colis c : colis) {
            valeur += c.getValeurDeclaree();
            if (c.getType() == TypeMarchandise.FRAGILE) {
                fragile = true;
            }
            if (c.getType() == TypeMarchandise.DANGEREUX) {
                dangereux = true;
            }
            if (c.getType() == TypeMarchandise.REFRIGERE) {
                refrigere = true;
            }
        }

        if (fragile) {
            prix *= COEFF_FRAGILE;
        }
        if (dangereux) {
            prix *= COEFF_DANGEREUX;
        }
        if (refrigere) {
            prix *= COEFF_REFRIGERE;
        }
        if (urgent) {
            prix *= COEFF_URGENCE;
        }

        prix = remise.appliquerRemise(prix);

        if (assurance) {
            double montantAssurance = valeur * TAUX_ASSURANCE;
            if (montantAssurance < MINIMUM_ASSURANCE) {
                montantAssurance = MINIMUM_ASSURANCE;
            }
            prix += montantAssurance;
        }

        return Utils.arrondir(prix);
    }

    public double calculerPrixHT(List<Colis> colis, int distance, String mode, boolean urgent, boolean assurance, String typeClient) {
        return calculerPrixHT(colis, distance, ModeTransport.depuisNom(mode), urgent, assurance, TypeClient.depuisNom(typeClient));
    }
}
