package fr.efrei.transfret;

import java.util.List;

public interface TarifTransport {
    double calculerTarifBase(List<Colis> colis, int distance);
}
