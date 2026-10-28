package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateursTest {

    @Test
    void supplementFragileApplique15PourcentSiFragilePresent() {
        CalculPrix base = ctx -> 100.0;
        CalculPrix decorateur = new SupplementFragile(base);

        ContexteCalculPrix avecFragile = new ContexteCalculPrix(
                List.of(new Colis("Vase", 1, 10, 10, 10, TypeMarchandise.FRAGILE, 0)),
                100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
        assertEquals(115.0, decorateur.calculer(avecFragile), 0.001);

        ContexteCalculPrix sansFragile = new ContexteCalculPrix(
                List.of(new Colis("Livre", 1, 10, 10, 10, TypeMarchandise.STANDARD, 0)),
                100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
        assertEquals(100.0, decorateur.calculer(sansFragile), 0.001);
    }

    @Test
    void supplementDangereuxApplique30Pourcent() {
        CalculPrix base = ctx -> 100.0;
        CalculPrix decorateur = new SupplementDangereux(base);

        ContexteCalculPrix avecDangereux = new ContexteCalculPrix(
                List.of(new Colis("Acide", 1, 10, 10, 10, TypeMarchandise.DANGEREUX, 0)),
                100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
        assertEquals(130.0, decorateur.calculer(avecDangereux), 0.001);
    }

    @Test
    void supplementUrgenceApplique50Pourcent() {
        CalculPrix base = ctx -> 100.0;
        CalculPrix decorateur = new SupplementUrgence(base);

        ContexteCalculPrix urgent = new ContexteCalculPrix(List.of(), 100, ModeTransport.ROUTE, true, false, TypeClient.PARTICULIER);
        assertEquals(150.0, decorateur.calculer(urgent), 0.001);
    }

    @Test
    void assuranceApplique2PourcentAvecMinimum15Euros() {
        CalculPrix base = ctx -> 100.0;
        CalculPrix decorateur = new DecorateurAssurance(base);

        // valeur = 500 € -> 2% = 10 € < 15 € -> minimum 15 €
        ContexteCalculPrix petiteValeur = new ContexteCalculPrix(
                List.of(new Colis("Bijou", 1, 10, 10, 10, TypeMarchandise.STANDARD, 500)),
                100, ModeTransport.ROUTE, false, true, TypeClient.PARTICULIER);
        assertEquals(115.0, decorateur.calculer(petiteValeur), 0.001);

        // valeur = 2000 € -> 2% = 40 € -> 40 €
        ContexteCalculPrix grandeValeur = new ContexteCalculPrix(
                List.of(new Colis("Machine", 1, 10, 10, 10, TypeMarchandise.STANDARD, 2000)),
                100, ModeTransport.ROUTE, false, true, TypeClient.PARTICULIER);
        assertEquals(140.0, decorateur.calculer(grandeValeur), 0.001);
    }
}
