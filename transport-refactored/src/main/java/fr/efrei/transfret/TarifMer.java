package fr.efrei.transfret;

import java.util.List;

public class TarifMer implements TarifTransport {

    public static final double TARIF_KM = 0.50;
    public static final double TARIF_KG = 0.03;
    public static final double MINIMUM = 500.0;

    @Override
    public double calculerTarifBase(List<Colis> colis, int distance) {
        double poids = colis.stream().mapToDouble(Colis::getPoids).sum();
        double prix = distance * TARIF_KM + poids * TARIF_KG;
        return Math.max(prix, MINIMUM);
    }
}
