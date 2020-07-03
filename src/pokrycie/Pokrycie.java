package pokrycie;

import zbiory.Zbiór;

/**
 * Zawiera deklarację metody czyPokrywa.
 * 
 * @author Michał Traczyk
 */
public abstract class Pokrycie {
    /**
     * Jest miarą wielkości zbioru, który ma zostać pokryty.
     */
    private final int koniec;

    public Pokrycie(int koniec) {
        this.koniec = koniec;
    }

    /**
     * Deklaracja metody czyPokrywa zaimplementowanej w podklasach.
     * 
     * @param rodzina rodzina zbiorów
     * @param moc     moc rodziny
     */
    public abstract void czyPokrywa(Zbiór[] rodzina, int moc);

    public int getKoniec() {
        return koniec;
    }
}
