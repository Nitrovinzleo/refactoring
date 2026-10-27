package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class ConsoleEmailService implements ServiceEmail {

    private final List<String[]> journal = new ArrayList<>();

    @Override
    public void envoyer(String destinataire, String sujet, String contenu) {
        journal.add(new String[]{destinataire, sujet, contenu});
        System.out.println("[EMAIL via smtp.transfret.fr] noreply@transfret.fr -> " + destinataire + " | " + sujet);
        System.out.println("\t" + contenu);
    }

    public List<String[]> getJournal() {
        return journal;
    }

    public void reinitialiser() {
        journal.clear();
    }
}
