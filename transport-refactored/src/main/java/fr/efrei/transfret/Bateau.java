package fr.efrei.transfret;

import java.util.List;

public class Bateau extends Vehicule {

    private final boolean conteneursRefrigeres;

    public Bateau(String immatriculation, double capaciteMax, boolean conteneursRefrigeres) {
        super(immatriculation, capaciteMax);
        this.conteneursRefrigeres = conteneursRefrigeres;
    }

    public boolean aConteneursRefrigeres() {
        return conteneursRefrigeres;
    }

    @Override
    public boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport mode) {
        if (mode != ModeTransport.MER) {
            return false;
        }
        boolean aRefrigere = colis.stream().anyMatch(c -> c.getType() == TypeMarchandise.REFRIGERE);
        if (aRefrigere && !conteneursRefrigeres) {
            return false;
        }
        return true;
    }

    @Override
    public void partir(int distanceKm) {
        int milesNautiques = (int) (distanceKm / 1.852);
        System.out.println("Le navire " + immatriculation + " navigue sur " + milesNautiques + " milles nautiques");
    }
}
