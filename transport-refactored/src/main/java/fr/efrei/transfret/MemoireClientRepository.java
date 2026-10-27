package fr.efrei.transfret;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoireClientRepository implements ClientRepository {

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public void sauvegarder(Client client) {
        System.out.println("[SQL] INSERT INTO client (nom, email, telephone, type) VALUES ('"
                + client.getNom() + "', '" + client.getEmail() + "', '" + client.getTelephone() + "', '"
                + client.getType() + "')");
        clients.put(client.getEmail(), client);
    }

    @Override
    public Optional<Client> trouverParEmail(String email) {
        return Optional.ofNullable(clients.get(email));
    }

    @Override
    public void reinitialiser() {
        clients.clear();
    }
}
