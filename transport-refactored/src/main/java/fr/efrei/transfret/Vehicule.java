package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicule implements IVehicule {

    protected String immatriculation;
    protected double capaciteMax;
    protected double chargeActuelle;
    protected boolean disponible = true;
    protected List<Colis> chargement = new ArrayList<>();

    public Vehicule(String immatriculation, double capaciteMax) {
        this.immatriculation = immatriculation;
        this.capaciteMax = capaciteMax;
    }

    @Override
    public String getImmatriculation() {
        return immatriculation;
    }

    @Override
    public double getCapaciteMax() {
        return capaciteMax;
    }

    @Override
    public double getChargeActuelle() {
        return chargeActuelle;
    }

    @Override
    public boolean estDisponible() {
        return disponible;
    }

    @Override
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public void charger(Colis colis) {
        if (chargeActuelle + colis.getPoids() > capaciteMax) {
            System.out.println("ERREUR : capacité dépassée pour " + immatriculation);
            return;
        }
        chargement.add(colis);
        chargeActuelle += colis.getPoids();
    }

    @Override
    public void decharger(Colis colis) {
        if (chargement.remove(colis)) {
            chargeActuelle -= colis.getPoids();
        }
    }

    @Override
    public void faireLePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }
}
