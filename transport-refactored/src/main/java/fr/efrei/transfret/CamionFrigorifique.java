package fr.efrei.transfret;

import java.util.List;

public class CamionFrigorifique extends Vehicule {

    public CamionFrigorifique(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax);
    }

    public void activerRefrigeration(double temperature) {
        System.out.println("Réfrigération activée à " + temperature + "°C sur " + immatriculation);
    }

    @Override
    public boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport mode) {
        if (mode != ModeTransport.ROUTE) {
            return false;
        }
        boolean aDangereux = colis.stream().anyMatch(c -> c.getType() == TypeMarchandise.DANGEREUX);
        if (aDangereux) {
            return false;
        }
        return colis.stream().allMatch(c -> c.getType() == TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distanceKm) {
        activerRefrigeration(4);
        System.out.println("Le camion " + immatriculation + " roule sur " + distanceKm + " km");
    }
}
