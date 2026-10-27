package fr.efrei.transfret;

import java.util.List;

public class TarifRoute implements TarifTransport {

    public static final double TARIF_KM = 1.20;
    public static final double TARIF_KG = 0.10;

    @Override
    public double calculerTarifBase(List<Colis> colis, int distance) {
        double poids = colis.stream().mapToDouble(Colis::getPoids).sum();
        return distance * TARIF_KM + poids * TARIF_KG;
    }
}
