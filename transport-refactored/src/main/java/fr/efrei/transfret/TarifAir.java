package fr.efrei.transfret;

import java.util.List;

public class TarifAir implements TarifTransport {

    public static final double TARIF_KM = 0.60;
    public static final double TARIF_KG_TAXABLE = 4.50;
    public static final double FACTEUR_VOLUMETRIQUE = 167.0;

    @Override
    public double calculerTarifBase(List<Colis> colis, int distance) {
        double poids = colis.stream().mapToDouble(Colis::getPoids).sum();
        double volume = colis.stream().mapToDouble(Colis::getVolume).sum();
        double poidsVolumetrique = volume * FACTEUR_VOLUMETRIQUE;
        double poidsTaxable = Math.max(poids, poidsVolumetrique);
        return distance * TARIF_KM + poidsTaxable * TARIF_KG_TAXABLE;
    }
}
