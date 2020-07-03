package pokrycie;

import java.util.Scanner;

import zbiory.ElementZbioru;
import zbiory.Zbiór;

/**
 * Rozszerza abstrakcyjną klasę Pokrycie. Zawiera implementację zadeklarowanej w
 * nadklasie metody czyPokrywa.
 * 
 * @author Michał Traczyk
 */
public class Dokładne extends Pokrycie {

    public Dokładne(int koniec) {
        super(koniec);
    }

    @Override
    public void czyPokrywa(Zbiór[] rodzina, int moc) {
        int[] tab = new int[getKoniec() + 1];
        boolean[] wynik = new boolean[moc];
        boolean[] aktualnyWynik = new boolean[moc];

        inicjalizuj(tab, wynik, aktualnyWynik);
        // Sprawdzam czy cała rodzina jest w stanie pokryć zadany zbiór.
        if (!czyIstniejePokrycie(rodzina, moc)) {
            System.out.println("0");
            return;
        }

        znajdźNajlepszePokrycie(0, rodzina, tab, moc, wynik, aktualnyWynik);
        wypiszWynik(wynik);
    }

    private static void wypiszWynik(boolean[] wynik) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < wynik.length; i++) {
            if (wynik[i]) {
                if (s.length() != 0) {
                    s.append(" ");
                }

                s.append((i + 1));
            }
        }

        System.out.println(s);
    }

    private boolean czyIstniejePokrycie(Zbiór[] rodzina, int moc) {
        int[] tab = new int[getKoniec() + 1];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = 0;
        }

        for (int i = 0; i < moc; i++) {
            poprawPokrycie(rodzina[i], tab, 1);
        }

        return czyPokrywa(tab);
    }

    private static void inicjalizuj(int[] tab, boolean[] wynik, boolean[] aktualnyWynik) {
        for (int i = 0; i < tab.length; i++) {
            tab[i] = 0;
        }

        for (int i = 0; i < wynik.length; i++) {
            wynik[i] = true;
            aktualnyWynik[i] = false;
        }
    }

    private void znajdźNajlepszePokrycie(int indeks, Zbiór[] rodzina, int[] tab, int moc,
            boolean[] wynik, boolean[] aktualnyWynik) {
        aktualnyWynik[indeks] = true;
        if (aktualnyWynik != nowyWynik(wynik, aktualnyWynik)) {
            aktualnyWynik[indeks] = false;
            return;
        }

        poprawPokrycie(rodzina[indeks], tab, 1);

        if (czyPokrywa(tab)) {
            przypisz(wynik, nowyWynik(wynik, aktualnyWynik));
            aktualnyWynik[indeks] = false;
            poprawPokrycie(rodzina[indeks], tab, -1);
            return;
        }

        if (indeks + 1 < moc) {
            znajdźNajlepszePokrycie(indeks + 1, rodzina, tab, moc, wynik, aktualnyWynik);
        } else {
            aktualnyWynik[indeks] = false;
            poprawPokrycie(rodzina[indeks], tab, -1);
            return;
        }

        poprawPokrycie(rodzina[indeks], tab, -1);
        aktualnyWynik[indeks] = false;
        znajdźNajlepszePokrycie(indeks + 1, rodzina, tab, moc, wynik, aktualnyWynik);
        return;
    }

    private static boolean czyPokrywa(int[] tab) {
        for (int i = 1; i < tab.length; i++) {
            if (tab[i] == 0) {
                return false;
            }
        }

        return true;
    }

    private static void przypisz(boolean[] wynik, boolean[] nowyWynik) {
        for (int i = 0; i < wynik.length; i++) {
            wynik[i] = nowyWynik[i];
        }
    }

    private static boolean[] nowyWynik(boolean[] aktualny, boolean[] kandydat) {
        int a = 0, b = 0;
        int n = aktualny.length;

        for (int i = 0; i < n; i++) {
            if (aktualny[i]) {
                a++;
            }

            if (kandydat[i]) {
                b++;
            }
        }

        if (a < b) {
            return aktualny;
        } else if (a > b) {
            return kandydat;
        }

        for (int i = 0; i < n; i++) {
            if (aktualny[i] != kandydat[i]) {
                if (aktualny[i]) {
                    return aktualny;
                } else {
                    return kandydat;
                }
            }
        }

        return aktualny;
    }

    /*
     * Argument typOperacji ma wartość 1 jeśli dodaję zbiór do pokrycia, -1 kiedy
     * odłączam zbiór od pokrycia.
     */
    private void poprawPokrycie(Zbiór zbiór, int[] tab, int typOperacji) {
        ElementZbioru[] zb = zbiór.getZbiór();
        int ileLiczb = 0;
        String napis;

        for (int i = 0; i < zbiór.getIle(); i++) {
            napis = zb[i].toString();
            /*
             * Zmienna napis reprezentuje element zbioru. Jeśli składa się z jednej liczby
             * to jest, to element pojedynczy. Z dwóch liczb - jest to element nieskończony,
             * z trzech liczb - jest to element skończony.
             */
            ileLiczb = napis.length() - napis.replace(" ", "").length();

            switch (ileLiczb) {
            case 0:
                poprawPokryciePojedynczym(napis, tab, typOperacji);
                break;
            case 1:
                poprawPokrycieNieskończonym(napis, tab, typOperacji);
                break;
            case 2:
                poprawPokrycieSkończonym(napis, tab, typOperacji);
            }
        }
    }

    private static void poprawPokryciePojedynczym(String napis, int[] tab, int typOperacji) {
        int a = Integer.parseInt(napis);

        if (a >= 1 && a < tab.length) {
            tab[a] += typOperacji;
        }
    }

    private static void poprawPokrycieNieskończonym(String napis, int[] tab, int typOperacji) {
        Scanner s = new Scanner(napis);
        int a = Integer.parseInt(s.next());
        int b = Integer.parseInt(s.next());

        for (int i = a; i < tab.length; i += b) {
            tab[i] += typOperacji;
        }

        s.close();
    }

    private static void poprawPokrycieSkończonym(String napis, int[] tab, int typOperacji) {
        Scanner s = new Scanner(napis);
        int a = Integer.parseInt(s.next());
        int b = Integer.parseInt(s.next());
        int c = Integer.parseInt(s.next());

        for (int i = a; i <= Math.min(tab.length - 1, b); i += c) {
            tab[i] += typOperacji;
        }

        s.close();
    }
}
