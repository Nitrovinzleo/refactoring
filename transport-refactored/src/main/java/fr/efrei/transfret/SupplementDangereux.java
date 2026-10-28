package fr.efrei.transfret;

public class SupplementDangereux extends DecorateurPrix {

    public static final double COEFF = 1.30;

    public SupplementDangereux(CalculPrix suivant) {
        super(suivant);
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        double montant = suivant.calculer(contexte);
        boolean aDangereux = contexte.colis().stream().anyMatch(c -> c.getType() == TypeMarchandise.DANGEREUX);
        return aDangereux ? montant * COEFF : montant;
    }
}
