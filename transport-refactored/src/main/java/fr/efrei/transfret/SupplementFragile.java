package fr.efrei.transfret;

public class SupplementFragile extends DecorateurPrix {

    public static final double COEFF = 1.15;

    public SupplementFragile(CalculPrix suivant) {
        super(suivant);
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        double montant = suivant.calculer(contexte);
        boolean aFragile = contexte.colis().stream().anyMatch(c -> c.getType() == TypeMarchandise.FRAGILE);
        return aFragile ? montant * COEFF : montant;
    }
}
