package fr.efrei.transfret;

public class SupplementHorsGabarit extends DecorateurPrix {

    public static final double COEFF = 1.10;
    public static final double SEUIL_DIMENSION_CM = 200.0;

    public SupplementHorsGabarit(CalculPrix suivant) {
        super(suivant);
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        double montant = suivant.calculer(contexte);
        boolean horsGabarit = contexte.colis().stream().anyMatch(c ->
                c.getLongueur() > SEUIL_DIMENSION_CM
                || c.getLargeur() > SEUIL_DIMENSION_CM
                || c.getHauteur() > SEUIL_DIMENSION_CM
        );
        return horsGabarit ? montant * COEFF : montant;
    }
}
