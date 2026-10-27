package fr.efrei.transfret;

public class RemisePremium implements PolitiqueRemise {

    public static final double TAUX = 0.80;

    @Override
    public double appliquerRemise(double montant) {
        return montant * TAUX;
    }
}
