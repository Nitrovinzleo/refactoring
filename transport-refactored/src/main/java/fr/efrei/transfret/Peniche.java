package fr.efrei.transfret;

import java.util.List;

public class Peniche extends Vehicule {

    public Peniche(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax);
    }

    @Override
    public boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport mode) {
        if (mode != ModeTransport.FLUVIAL) {
            return false;
        }
        boolean aRefrigere = colis.stream().anyMatch(c -> c.getType() == TypeMarchandise.REFRIGERE);
        return !aRefrigere;
    }

    @Override
    public void partir(int distanceKm) {
        System.out.println("La péniche " + immatriculation + " navigue sur " + distanceKm + " km");
    }
}
