package fr.efrei.transfret;

public class RemiseParticulier implements PolitiqueRemise {

    @Override
    public double appliquerRemise(double montant) {
        return montant;
    }
}
