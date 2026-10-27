package fr.efrei.transfret;

public class Notificateur {

    private final ServiceEmail serviceEmail;
    private final ServiceSms serviceSms;

    public Notificateur(ServiceEmail serviceEmail, ServiceSms serviceSms) {
        this.serviceEmail = serviceEmail;
        this.serviceSms = serviceSms;
    }

    public void notifier(Client client, String sujet, String corps, String sms) {
        serviceEmail.envoyer(client.getEmail(), sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            serviceSms.envoyer(client.getTelephone(), sms);
        }
    }
}
