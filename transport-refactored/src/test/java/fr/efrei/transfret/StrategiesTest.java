package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategiesTest {

    @Test
    void tarifRouteCalculeCorrectement() {
        TarifRoute tarif = new TarifRoute();
        List<Colis> colis = List.of(new Colis("Test", 100, 10, 10, 10, TypeMarchandise.STANDARD, 0));
        // 500 km * 1.2 + 100 kg * 0.10 = 600 + 10 = 610.0
        assertEquals(610.0, tarif.calculerTarifBase(colis, 500));
    }

    @Test
    void tarifRailAppliqueMinimum() {
        TarifRail tarif = new TarifRail();
        List<Colis> colis = List.of(new Colis("Test", 10, 10, 10, 10, TypeMarchandise.STANDARD, 0));
        // 50 km * 0.8 + 10 kg * 0.07 = 40 + 0.7 = 40.7 -> min 150
        assertEquals(150.0, tarif.calculerTarifBase(colis, 50));
    }

    @Test
    void tarifMerAppliqueMinimum() {
        TarifMer tarif = new TarifMer();
        List<Colis> colis = List.of(new Colis("Test", 50, 10, 10, 10, TypeMarchandise.STANDARD, 0));
        // 100 km * 0.5 + 50 kg * 0.03 = 50 + 1.5 = 51.5 -> min 500
        assertEquals(500.0, tarif.calculerTarifBase(colis, 100));
    }

    @Test
    void tarifAirPrendPoidsVolumetriqueSiSuperieur() {
        TarifAir tarif = new TarifAir();
        // Colis de 1 m3 (100x100x100 cm) et 50 kg -> volume = 1 m3 -> volumétrique = 167 kg > 50 kg
        List<Colis> colis = List.of(new Colis("Grand carton", 50, 100, 100, 100, TypeMarchandise.STANDARD, 0));
        // 1000 km * 0.6 + 167 kg * 4.5 = 600 + 751.5 = 1351.5
        assertEquals(1351.5, tarif.calculerTarifBase(colis, 1000));
    }

    @Test
    void remisesClientsSontBienCalculees() {
        PolitiqueRemise particulier = new RemiseParticulier();
        assertEquals(1000.0, particulier.appliquerRemise(1000.0));

        PolitiqueRemise entreprise = new RemiseEntreprise();
        assertEquals(900.0, entreprise.appliquerRemise(1000.0)); // 10% de remise si <= 5000
        assertEquals(5100.0, entreprise.appliquerRemise(6000.0)); // 15% de remise si > 5000 (6000 * 0.85 = 5100)

        PolitiqueRemise premium = new RemisePremium();
        assertEquals(800.0, premium.appliquerRemise(1000.0)); // 20%
    }
}
