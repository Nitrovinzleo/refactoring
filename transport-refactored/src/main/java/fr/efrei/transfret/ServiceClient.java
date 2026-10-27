package fr.efrei.transfret;

public class ServiceClient {

    private final ValidateurClient validateur = new ValidateurClient();

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        if (!validateur.valider(nom, email, type, siret)) {
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        Database.getInstance().sauvegarderClient(c);
        EmailService.envoyer(c.getEmail(), "Bienvenue chez TransFret", "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.");
        return c;
    }

    public Client creerClient(String nom, String email, String telephone, String adresse, String typeStr, String siret) {
        TypeClient type = TypeClient.depuisNom(typeStr);
        return creerClient(nom, email, telephone, adresse, type, siret);
    }
}
