package fr.efrei.transfret;

public enum StatutExpedition {
    CREEE,
    EN_COURS,
    LIVREE,
    ANNULEE;

    public static StatutExpedition depuisNom(String nom) {
        if (nom == null) {
            return null;
        }
        try {
            return StatutExpedition.valueOf(nom.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
