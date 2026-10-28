package fr.efrei.transfret;

public abstract class DecorateurPrix implements CalculPrix {

    protected final CalculPrix suivant;

    public DecorateurPrix(CalculPrix suivant) {
        this.suivant = suivant;
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        return suivant.calculer(contexte);
    }
}
