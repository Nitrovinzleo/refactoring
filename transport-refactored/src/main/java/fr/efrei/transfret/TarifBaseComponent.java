package fr.efrei.transfret;

public class TarifBaseComponent implements CalculPrix {

    private final TarifTransport tarif;

    public TarifBaseComponent(TarifTransport tarif) {
        this.tarif = tarif;
    }

    @Override
    public double calculer(ContexteCalculPrix contexte) {
        return tarif.calculerTarifBase(contexte.colis(), contexte.distance());
    }
}
