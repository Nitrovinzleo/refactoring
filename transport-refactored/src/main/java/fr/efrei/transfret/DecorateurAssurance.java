package fr.efrei.transfret;

public class DecorateurAssurance extends DecorateurPrix {

    public static final double TAUX = 0.02;
    public static final double MINIMUM = 15.0;

    public DecorateurAssurance(CalculPrix suivant) {
        super(suivant);
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        double montant = suivant.calculer(contexte);
        if (!contexte.assurance()) {
            return montant;
        }
        double valeur = contexte.colis().stream().mapToDouble(Colis::getValeurDeclaree).sum();
        double assurance = Math.max(valeur * TAUX, MINIMUM);
        return montant + assurance;
    }
}
