package fr.efrei.transfret;

import java.util.List;

public class Camion extends Vehicule {

    protected boolean adr;

    public Camion(String immatriculation, double capaciteMax, boolean adr) {
        super(immatriculation, capaciteMax);
        this.adr = adr;
    }

    public boolean peutTransporterMatieresDangereuses() {
        return adr;
    }

    @Override
    public boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport mode) {
        if (mode != ModeTransport.ROUTE) {
            return false;
        }
        boolean aRefrigere = colis.stream().anyMatch(c -> c.getType() == TypeMarchandise.REFRIGERE);
        if (aRefrigere) {
            return false;
        }
        boolean aDangereux = colis.stream().anyMatch(c -> c.getType() == TypeMarchandise.DANGEREUX);
        if (aDangereux && !adr) {
            return false;
        }
        return true;
    }

    @Override
    public void partir(int distanceKm) {
        System.out.println("Le camion " + immatriculation + " roule sur " + distanceKm + " km");
    }
}
