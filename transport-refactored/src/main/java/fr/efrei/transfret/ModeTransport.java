package fr.efrei.transfret;

public enum ModeTransport {
    ROUTE,
    RAIL,
    MER,
    AIR,
    FLUVIAL;

    public static ModeTransport depuisNom(String nom) {
        if (nom == null) {
            return null;
        }
        try {
            return ModeTransport.valueOf(nom.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
