package zbiory;

import java.util.Arrays;

/**
 * Klasa reprezentująca zbiór.
 * 
 * @author Michał Traczyk
 */
public class Zbiór {

    private ElementZbioru[] zbiór;
    private int ile;

    private static final int MNOŻNIK = 3;
    private static final int DZIELNIK = 2;

    public Zbiór() {
        this.ile = 0;
        zbiór = new ElementZbioru[0];
    }

    private static int więcej(int d) {
        return 1 + d * MNOŻNIK / DZIELNIK;
    }

    public void dodajElement(ElementZbioru dodawany) {
        if (ile == zbiór.length) {
            zbiór = Arrays.copyOf(zbiór, więcej(zbiór.length));
        }

        zbiór[ile++] = dodawany;
    }

    public ElementZbioru[] getZbiór() {
        return zbiór;
    }

    public int getIle() {
        return ile;
    }

}
