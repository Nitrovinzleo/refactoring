package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionsTest {

    @Test
    void tarifFluvialCalculeCorrectementEtAppliqueMinimum() {
        TarifFluvial tarif = new TarifFluvial();

        // 365 km et 1000 kg : 365 * 0.40 + 1000 * 0.02 = 146 + 20 = 166 € -> minimum 300 €
        List<Colis> petitColis = List.of(new Colis("Cartons", 1000, 100, 100, 100, TypeMarchandise.STANDARD, 0));
        assertEquals(300.0, tarif.calculerTarifBase(petitColis, 365));

        // 365 km et 20000 kg : 365 * 0.40 + 20000 * 0.02 = 146 + 400 = 546 €
        List<Colis> lourdColis = List.of(new Colis("Acier", 20000, 100, 100, 100, TypeMarchandise.STANDARD, 0));
        assertEquals(546.0, tarif.calculerTarifBase(lourdColis, 365));
    }

    @Test
    void delaiFluvialEstConforme() {
        EstimateurDelai estimateur = new EstimateurDelai();
        // 365 km : 365 / 360 = 1.014 -> ceil = 2 + 1 = 3 jours
        assertEquals(3, estimateur.calculerDelai(365, ModeTransport.FLUVIAL, false));
    }

    @Test
    void supplementHorsGabaritSAppliqueSiUneDimensionDepasse200cm() {
        CalculPrix base = ctx -> 1000.0;
        CalculPrix decorateur = new SupplementHorsGabarit(base);

        // Longueur 210 cm (> 200 cm)
        ContexteCalculPrix horsGabarit = new ContexteCalculPrix(
                List.of(new Colis("Poutre", 50, 210, 30, 30, TypeMarchandise.STANDARD, 0)),
                100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
        assertEquals(1100.0, decorateur.calculer(horsGabarit), 0.001);

        // Dimensions standards (150x80x80 cm)
        ContexteCalculPrix gabaritNormal = new ContexteCalculPrix(
                List.of(new Colis("Meuble", 50, 150, 80, 80, TypeMarchandise.STANDARD, 0)),
                100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
        assertEquals(1000.0, decorateur.calculer(gabaritNormal), 0.001);
    }

    @Test
    void penichePrendEnChargeFluvialEtRefuseRefrigere() {
        Peniche peniche = new Peniche("PEN-0001", 2500000);

        List<Colis> standard = List.of(new Colis("Grain", 50000, 100, 100, 100, TypeMarchandise.STANDARD, 0));
        assertTrue(peniche.peutPrendreEnCharge(standard, ModeTransport.FLUVIAL));
        assertFalse(peniche.peutPrendreEnCharge(standard, ModeTransport.ROUTE));

        List<Colis> refrigere = List.of(new Colis("Viande", 1000, 100, 100, 100, TypeMarchandise.REFRIGERE, 0));
        assertFalse(peniche.peutPrendreEnCharge(refrigere, ModeTransport.FLUVIAL));
    }

    @Test
    void calculDevisFluvialAvecHorsGabarit() {
        CalculateurPrix calculateur = new CalculateurPrix();
        calculateur.setActiverHorsGabarit(true);
        // Paris -> Le Havre = 365 km
        // Colis de 20 000 kg, dimension 250 cm (> 200 cm)
        // Tarif base = 365 * 0.40 + 20000 * 0.02 = 546 €
        // Hors gabarit (x1.10) = 546 * 1.10 = 600.60 €
        List<Colis> colis = List.of(new Colis("Poutrelles", 20000, 250, 50, 50, TypeMarchandise.STANDARD, 0));
        double prixHT = calculateur.calculerPrixHT(colis, 365, ModeTransport.FLUVIAL, false, false, TypeClient.PARTICULIER);
        assertEquals(600.60, prixHT);
    }
}
