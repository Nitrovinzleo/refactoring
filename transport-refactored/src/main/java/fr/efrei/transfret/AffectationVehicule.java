package fr.efrei.transfret;

import java.util.List;

public class AffectationVehicule {

    private final Flotte flotte;

    public AffectationVehicule(Flotte flotte) {
        this.flotte = flotte;
    }

    public Vehicule trouverVehicule(List<Colis> colis, ModeTransport mode) {
        double poids = colis.stream().mapToDouble(Colis::getPoids).sum();
        for (Vehicule v : flotte.getVehicules()) {
            if (!v.estDisponible()) {
                continue;
            }
            if (v.getCapaciteMax() - v.getChargeActuelle() < poids) {
                continue;
            }
            if (v.peutPrendreEnCharge(colis, mode)) {
                return v;
            }
        }
        return null;
    }

    public Vehicule trouverVehicule(List<Colis> colis, String mode) {
        return trouverVehicule(colis, ModeTransport.depuisNom(mode));
    }
}
