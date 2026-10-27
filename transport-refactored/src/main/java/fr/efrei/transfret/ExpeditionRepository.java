package fr.efrei.transfret;

import java.util.List;
import java.util.Optional;

public interface ExpeditionRepository {
    void sauvegarder(Expedition expedition);
    void mettreAJourStatut(Expedition expedition);
    Optional<Expedition> trouverParId(String id);
    List<Expedition> toutes();
    void reinitialiser();
}
