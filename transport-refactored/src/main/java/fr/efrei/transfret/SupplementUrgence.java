package fr.efrei.transfret;

public class SupplementUrgence extends DecorateurPrix {

    public static final double COEFF = 1.50;

    public SupplementUrgence(CalculPrix suivant) {
        super(suivant);
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        double montant = suivant.calculer(contexte);
        return contexte.urgent() ? montant * COEFF : montant;
    }
}
