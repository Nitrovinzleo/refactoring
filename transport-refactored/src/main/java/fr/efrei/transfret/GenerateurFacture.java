package fr.efrei.transfret;

import java.util.Date;

public class GenerateurFacture {

    public String genererFacture(Expedition expedition) {
        StringBuilder sb = new StringBuilder();
        sb.append("==============================================\n");
        sb.append("              FACTURE TRANSFRET\n");
        sb.append("==============================================\n");
        sb.append("Facture n° F-").append(expedition.getId()).append("\n");
        sb.append("Date : ").append(Utils.formaterDate(new Date())).append("\n");
        sb.append("Client : ").append(expedition.getClient().getNom()).append("\n");
        if (expedition.getClient().getType() == TypeClient.ENTREPRISE) {
            sb.append("SIRET : ").append(expedition.getClient().getSiret()).append("\n");
        }
        sb.append("Adresse : ").append(expedition.getClient().getAdresse()).append("\n");
        sb.append("----------------------------------------------\n");
        sb.append("Trajet : ").append(expedition.getVilleDepart()).append(" -> ")
                .append(expedition.getVilleArrivee()).append(" (")
                .append(expedition.getDistance()).append(" km)\n");
        sb.append("Mode : ").append(expedition.getMode())
                .append(expedition.isUrgent() ? " (URGENT)" : "").append("\n");
        sb.append("Colis :\n");
        for (Colis c : expedition.getColis()) {
            sb.append("  - ").append(c.getDescription()).append(" : ")
                    .append(c.getPoids()).append(" kg [")
                    .append(c.getLibelleType()).append("]\n");
        }
        sb.append("----------------------------------------------\n");
        if (expedition.getStatut() == StatutExpedition.ANNULEE) {
            sb.append("EXPEDITION ANNULEE\n");
            sb.append("Frais d'annulation TTC : ")
                    .append(String.format("%.2f", expedition.getFraisAnnulation()))
                    .append(" €\n");
        } else {
            sb.append("Total HT  : ")
                    .append(String.format("%.2f", expedition.getPrixHT()))
                    .append(" €\n");
            sb.append("TVA 20%   : ")
                    .append(String.format("%.2f", expedition.getPrixTTC() - expedition.getPrixHT()))
                    .append(" €\n");
            sb.append("Total TTC : ")
                    .append(String.format("%.2f", expedition.getPrixTTC()))
                    .append(" €\n");
        }
        sb.append("==============================================\n");
        return sb.toString();
    }
}
