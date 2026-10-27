package fr.efrei.transfret;

public class ValidateurClient {

    public static final int LONGUEUR_SIRET = 14;

    public boolean valider(String nom, String email, TypeClient type, String siret) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("ERREUR : nom obligatoire");
            return false;
        }
        if (!Utils.emailValide(email)) {
            System.out.println("ERREUR : email invalide (" + email + ")");
            return false;
        }
        if (type == null) {
            System.out.println("ERREUR : type de client inconnu");
            return false;
        }
        if (type == TypeClient.ENTREPRISE && (siret == null || siret.length() != LONGUEUR_SIRET)) {
            System.out.println("ERREUR : SIRET obligatoire (14 chiffres) pour une entreprise");
            return false;
        }
        return true;
    }
}
