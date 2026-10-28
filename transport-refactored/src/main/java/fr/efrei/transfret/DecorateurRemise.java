package fr.efrei.transfret;

public class DecorateurRemise extends DecorateurPrix {

    private final PolitiqueRemise politique;

    public DecorateurRemise(CalculPrix suivant, PolitiqueRemise politique) {
        super(suivant);
        this.politique = politique;
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        double montant = suivant.calculer(contexte);
        return politique != null ? politique.appliquerRemise(montant) : montant;
    }
}
