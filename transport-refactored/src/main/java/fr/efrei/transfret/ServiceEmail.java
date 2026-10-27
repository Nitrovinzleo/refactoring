package fr.efrei.transfret;

public interface ServiceEmail {
    void envoyer(String destinataire, String sujet, String contenu);
}
