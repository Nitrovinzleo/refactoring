package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class ConsoleSmsService implements ServiceSms {

    private final List<String[]> journal = new ArrayList<>();

    @Override
    public void envoyer(String destinataire, String message) {
        journal.add(new String[]{destinataire, message});
        System.out.println("[SMS] -> " + destinataire + " : " + message);
    }

    public List<String[]> getJournal() {
        return journal;
    }

    public void reinitialiser() {
        journal.clear();
    }
}
