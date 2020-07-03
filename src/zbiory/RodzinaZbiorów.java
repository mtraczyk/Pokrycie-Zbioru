package zbiory;

import java.util.Arrays;

/**
 * Klasa reprezentująca rodzinę zbiorów.
 * 
 * @author Michał Traczyk
 */
public class RodzinaZbiorów {

    private Zbiór[] rodzina;
    private int ile;

    private static final int MNOŻNIK = 3;
    private static final int DZIELNIK = 2;

    public RodzinaZbiorów() {
        this.ile = 0;
        rodzina = new Zbiór[0];
    }

    private static int więcej(int d) {
        return 1 + d * MNOŻNIK / DZIELNIK;
    }

    public void dodajZbiór(Zbiór dodawany) {
        if (ile == rodzina.length) {
            rodzina = Arrays.copyOf(rodzina, więcej(rodzina.length));
        }

        rodzina[ile++] = dodawany;
    }

    public Zbiór[] getRodzina() {
        return rodzina;
    }

    public int getIle() {
        return ile;
    }
}
