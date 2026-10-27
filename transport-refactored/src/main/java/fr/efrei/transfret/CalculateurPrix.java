package fr.efrei.transfret;

import java.util.List;

public class CalculateurPrix {

    public static final double TARIF_KM_ROUTE = 1.20;
    public static final double TARIF_KG_ROUTE = 0.10;

    public static final double TARIF_KM_RAIL = 0.80;
    public static final double TARIF_KG_RAIL = 0.07;
    public static final double MINIMUM_RAIL = 150.0;

    public static final double TARIF_KM_MER = 0.50;
    public static final double TARIF_KG_MER = 0.03;
    public static final double MINIMUM_MER = 500.0;

    public static final double TARIF_KM_AIR = 0.60;
    public static final double TARIF_KG_TAXABLE_AIR = 4.50;
    public static final double FACTEUR_VOLUMETRIQUE_AIR = 167.0;

    public static final double COEFF_FRAGILE = 1.15;
    public static final double COEFF_DANGEREUX = 1.30;
    public static final double COEFF_REFRIGERE = 1.20;
    public static final double COEFF_URGENCE = 1.50;

    public static final double SEUIL_REMISE_ENTREPRISE = 5000.0;
    public static final double FACTEUR_REMISE_ENTREPRISE_STANDARD = 0.90;
    public static final double FACTEUR_REMISE_ENTREPRISE_HAUTE = 0.85;
    public static final double FACTEUR_REMISE_PREMIUM = 0.80;

    public static final double TAUX_ASSURANCE = 0.02;
    public static final double MINIMUM_ASSURANCE = 15.0;

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        double poids = 0;
        double volume = 0;
        double valeur = 0;
        boolean fragile = false;
        boolean dangereux = false;
        boolean refrigere = false;

        for (Colis c : colis) {
            poids += c.getPoids();
            volume += c.getVolume();
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

        double prix;
        if (mode == ModeTransport.ROUTE) {
            prix = distance * TARIF_KM_ROUTE + poids * TARIF_KG_ROUTE;
        } else if (mode == ModeTransport.RAIL) {
            prix = distance * TARIF_KM_RAIL + poids * TARIF_KG_RAIL;
            if (prix < MINIMUM_RAIL) {
                prix = MINIMUM_RAIL;
            }
        } else if (mode == ModeTransport.MER) {
            prix = distance * TARIF_KM_MER + poids * TARIF_KG_MER;
            if (prix < MINIMUM_MER) {
                prix = MINIMUM_MER;
            }
        } else if (mode == ModeTransport.AIR) {
            double poidsTaxable = poids;
            if (volume * FACTEUR_VOLUMETRIQUE_AIR > poids) {
                poidsTaxable = volume * FACTEUR_VOLUMETRIQUE_AIR;
            }
            prix = distance * TARIF_KM_AIR + poidsTaxable * TARIF_KG_TAXABLE_AIR;
        } else {
            throw new IllegalArgumentException("Mode inconnu : " + mode);
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

        if (typeClient == TypeClient.ENTREPRISE) {
            if (prix > SEUIL_REMISE_ENTREPRISE) {
                prix *= FACTEUR_REMISE_ENTREPRISE_HAUTE;
            } else {
                prix *= FACTEUR_REMISE_ENTREPRISE_STANDARD;
            }
        } else if (typeClient == TypeClient.PREMIUM) {
            prix *= FACTEUR_REMISE_PREMIUM;
        }

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
