package fr.efrei.transfret;

public class EstimateurDelai {

    public static final double VITESSE_ROUTE_KMH = 70.0;
    public static final double HEURES_JOUR_STANDARD = 9.0;
    public static final double HEURES_JOUR_URGENT = 18.0;
    public static final double CADENCE_KM_JOUR_RAIL = 1440.0;
    public static final double CADENCE_KM_JOUR_MER = 720.0;
    public static final int SEUIL_DISTANCE_AIR = 3000;

    public int calculerDelai(int distance, ModeTransport mode, boolean urgent) {
        int jours = 0;
        if (mode == ModeTransport.ROUTE) {
            double heures = distance / VITESSE_ROUTE_KMH;
            if (urgent) {
                jours = (int) Math.ceil(heures / HEURES_JOUR_URGENT);
            } else {
                jours = (int) Math.ceil(heures / HEURES_JOUR_STANDARD);
            }
        } else if (mode == ModeTransport.RAIL) {
            jours = (int) Math.ceil(distance / CADENCE_KM_JOUR_RAIL) + 1;
            if (urgent) {
                jours = jours - 1;
            }
        } else if (mode == ModeTransport.MER) {
            jours = (int) Math.ceil(distance / CADENCE_KM_JOUR_MER) + 2;
        } else if (mode == ModeTransport.AIR) {
            jours = 1;
            if (distance > SEUIL_DISTANCE_AIR) {
                jours = 2;
            }
            if (urgent) {
                jours = 1;
            }
        }
        if (jours < 1) {
            jours = 1;
        }
        return jours;
    }
}
