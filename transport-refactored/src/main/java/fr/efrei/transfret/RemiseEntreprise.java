package fr.efrei.transfret;

public class RemiseEntreprise implements PolitiqueRemise {

    public static final double SEUIL = 5000.0;
    public static final double TAUX_STANDARD = 0.90;
    public static final double TAUX_ELEVE = 0.85;

    @Override
    public double appliquerRemise(double montant) {
        if (montant > SEUIL) {
            return montant * TAUX_ELEVE;
        }
        return montant * TAUX_STANDARD;
    }
}
