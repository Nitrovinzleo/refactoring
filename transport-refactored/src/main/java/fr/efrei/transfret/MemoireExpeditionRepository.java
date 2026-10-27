package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoireExpeditionRepository implements ExpeditionRepository {

    private final Map<String, Expedition> expeditions = new HashMap<>();

    @Override
    public void sauvegarder(Expedition expedition) {
        System.out.println("[SQL] INSERT INTO expedition (id, client, depart, arrivee, mode, statut, prix_ttc) VALUES ('"
                + expedition.getId() + "', '" + expedition.getClient().getEmail() + "', '"
                + expedition.getVilleDepart() + "', '" + expedition.getVilleArrivee() + "', '"
                + expedition.getMode() + "', '" + expedition.getStatut() + "', "
                + expedition.getPrixTTC() + ")");
        expeditions.put(expedition.getId(), expedition);
    }

    @Override
    public void mettreAJourStatut(Expedition expedition) {
        System.out.println("[SQL] UPDATE expedition SET statut = '"
                + expedition.getStatut() + "' WHERE id = '" + expedition.getId() + "'");
    }

    @Override
    public Optional<Expedition> trouverParId(String id) {
        System.out.println("[SQL] SELECT * FROM expedition WHERE id = '" + id + "'");
        return Optional.ofNullable(expeditions.get(id));
    }

    @Override
    public List<Expedition> toutes() {
        System.out.println("[SQL] SELECT * FROM expedition");
        return new ArrayList<>(expeditions.values());
    }

    @Override
    public void reinitialiser() {
        expeditions.clear();
    }
}
