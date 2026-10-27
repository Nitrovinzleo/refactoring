package fr.efrei.transfret;

import java.util.List;

public class ValidateurExpedition {

    public boolean valider(Client client, List<Colis> colis, String villeDepart, String villeArrivee, ModeTransport mode, boolean urgent, int distance) {
        if (client == null) {
            System.out.println("ERREUR : client obligatoire");
            return false;
        }
        if (colis == null || colis.isEmpty()) {
            System.out.println("ERREUR : au moins un colis est nécessaire");
            return false;
        }
        boolean dangereux = false;
        boolean refrigere = false;
        for (Colis c : colis) {
            if (c.getPoids() <= 0) {
                System.out.println("ERREUR : poids invalide pour " + c.getDescription());
                return false;
            }
            if (c.getType() == TypeMarchandise.DANGEREUX) {
                dangereux = true;
            }
            if (c.getType() == TypeMarchandise.REFRIGERE) {
                refrigere = true;
            }
        }
        if (dangereux && mode == ModeTransport.AIR) {
            System.out.println("ERREUR : les matières dangereuses sont interdites en transport aérien");
            return false;
        }
        if (refrigere && !(mode == ModeTransport.ROUTE || mode == ModeTransport.MER)) {
            System.out.println("ERREUR : le transport réfrigéré n'est possible que par route ou par mer");
            return false;
        }
        if (urgent && mode == ModeTransport.MER) {
            System.out.println("ERREUR : pas d'option urgente pour le transport maritime");
            return false;
        }
        if (distance == -1) {
            System.out.println("ERREUR : trajet inconnu " + villeDepart + " -> " + villeArrivee);
            return false;
        }
        return true;
    }
}
