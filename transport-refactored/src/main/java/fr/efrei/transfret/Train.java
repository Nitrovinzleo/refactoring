package fr.efrei.transfret;

import java.util.List;

public class Train extends Vehicule {

    public Train(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax);
    }

    @Override
    public boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport mode) {
        return mode == ModeTransport.RAIL;
    }

    @Override
    public void faireLePlein() {
        // Un train électrique ne fait pas le plein
    }

    @Override
    public void partir(int distanceKm) {
        System.out.println("Le train " + immatriculation + " circule sur " + distanceKm + " km de voies ferrées");
    }
}
