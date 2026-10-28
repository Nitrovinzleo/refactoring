package fr.efrei.transfret;

import java.util.List;

public class Avion extends Vehicule {

    public Avion(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax);
    }

    @Override
    public boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport mode) {
        if (mode != ModeTransport.AIR) {
            return false;
        }
        boolean aDangereux = colis.stream().anyMatch(c -> c.getType() == TypeMarchandise.DANGEREUX);
        return !aDangereux;
    }

    @Override
    public void partir(int distanceKm) {
        System.out.println("L'avion " + immatriculation + " roule sur la piste");
        System.out.println("L'avion " + immatriculation + " vole à 35000 pieds");
    }
}
