package fr.efrei.transfret;

public class SupplementRefrigere extends DecorateurPrix {

    public static final double COEFF = 1.20;

    public SupplementRefrigere(CalculPrix suivant) {
        super(suivant);
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        double montant = suivant.calculer(contexte);
        boolean aRefrigere = contexte.colis().stream().anyMatch(c -> c.getType() == TypeMarchandise.REFRIGERE);
        return aRefrigere ? montant * COEFF : montant;
    }
}
