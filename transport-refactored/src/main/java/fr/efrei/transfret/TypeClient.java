package fr.efrei.transfret;

public enum TypeClient {
    PARTICULIER,
    ENTREPRISE,
    PREMIUM;

    public static TypeClient depuisNom(String nom) {
        if (nom == null) {
            return null;
        }
        try {
            return TypeClient.valueOf(nom.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
