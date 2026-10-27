package fr.efrei.transfret;

import java.util.List;

public class TarifRail implements TarifTransport {

    public static final double TARIF_KM = 0.80;
    public static final double TARIF_KG = 0.07;
    public static final double MINIMUM = 150.0;

    @Override
    public double calculerTarifBase(List<Colis> colis, int distance) {
        double poids = colis.stream().mapToDouble(Colis::getPoids).sum();
        double prix = distance * TARIF_KM + poids * TARIF_KG;
        return Math.max(prix, MINIMUM);
    }
}
