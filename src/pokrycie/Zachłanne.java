package pokrycie;

import java.util.Arrays;
import java.util.Scanner;

import zbiory.ElementZbioru;
import zbiory.Zbiór;

/**
 * Rozszerza abstrakcyjną klasę Pokrycie. Zawiera implementację zadeklarowanej w
 * nadklasie metody czyPokrywa.
 * 
 * @author Michał Traczyk
 */
public class Zachłanne extends Pokrycie {

    public Zachłanne(int koniec) {
        super(koniec);
    }

    @Override
    public void czyPokrywa(Zbiór[] rodzina, int moc) {
        boolean[] wykorzystane = new boolean[moc];
        boolean[] tab = new boolean[getKoniec() + 1];

        ustawWartości(wykorzystane, tab);
        znajdźOdpowiedź(rodzina, wykorzystane, tab);
    }

    private static void ustawWartości(boolean[] wykorzystane, boolean[] tab) {
        for (int i = 0; i < wykorzystane.length; i++) {
            wykorzystane[i] = false;
        }

        for (int i = 0; i < tab.length; i++) {
            tab[i] = false;
        }
    }

    private void znajdźOdpowiedź(Zbiór[] rodzina, boolean[] wykorzystane, boolean[] tab) {
        int a = 0;

        while (a != -1) {
            /*
             * Metoda któryPoprawiaPokrycieNajbardziej zwraca indeks zbioru rodziny zbiorów,
             * który poprawia pokrycie najbardziej. -1 jeśli nie ma już żadnego zbioru,
             * który poprawia wynik.
             */
            a = któryPoprawiaPokrycieNajbardziej(rodzina, wykorzystane, tab);
            if (a != -1) {
                wykorzystane[a] = true;
                /*
                 * Wywołuję metodę oIlePoprawiaPokrycie w celu zaktualizowania aktualnego
                 * pokrycia reprezentowanego przez tablicę tab.
                 */
                oIlePoprawiaPokrycie(rodzina[a], tab);
            }
        }

        wypiszWynik(wykorzystane, tab);
    }

    private static void wypiszWynik(boolean[] wykorzystane, boolean[] tab) {
        for (int i = 1; i < tab.length; i++) {
            if (!tab[i]) {
                System.out.println("0");
                return;
            }
        }

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < wykorzystane.length; i++) {
            if (wykorzystane[i]) {
                if (s.length() != 0) {
                    s.append(" ");
                }
                s.append(i + 1);
            }
        }

        System.out.println(s);
    }

    /*
     * Metoda któryPoprawiaPokrycieNajbardziej zwraca indeks zbioru rodziny zbiorów,
     * który poprawia pokrycie najbardziej. -1 jeśli nie ma już żadnego zbioru,
     * który poprawia wynik.
     */
    private int któryPoprawiaPokrycieNajbardziej(Zbiór[] rodzina, boolean[] wykorzystane,
            boolean[] tab) {
        int wynik = 0, pom = 0;
        int najlepszy = -1;

        for (int i = 0; i < wykorzystane.length; i++) {
            if (!wykorzystane[i]) {
                pom = oIlePoprawiaPokrycie(rodzina[i], Arrays.copyOf(tab, tab.length));
                if (pom > wynik) {
                    wynik = pom;
                    najlepszy = i;
                }
            }
        }

        return najlepszy;
    }

    private int oIlePoprawiaPokrycie(Zbiór zbiór, boolean[] tab) {
        ElementZbioru[] zb = zbiór.getZbiór();
        int ileLiczb = 0;
        int nowoPokryte = 0;
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
                nowoPokryte += poprawPokryciePojedynczym(napis, tab);
                break;
            case 1:
                nowoPokryte += poprawPokrycieNieskończonym(napis, tab);
                break;
            case 2:
                nowoPokryte += poprawPokrycieSkończonym(napis, tab);
            }
        }

        return nowoPokryte;
    }

    private static int poprawPokryciePojedynczym(String napis, boolean[] tab) {
        int a = Integer.parseInt(napis);
        int nowoPokryte = 0;

        if (a >= 1 && a < tab.length && !tab[a]) {
            tab[a] = true;
            nowoPokryte++;
        }

        return nowoPokryte;
    }

    private static int poprawPokrycieNieskończonym(String napis, boolean[] tab) {
        Scanner s = new Scanner(napis);
        int nowoPokryte = 0;
        int a = Integer.parseInt(s.next());
        int b = Integer.parseInt(s.next());

        for (int i = a; i < tab.length; i += b) {
            if (!tab[i]) {
                nowoPokryte++;
            }
            tab[i] = true;
        }

        s.close();
        return nowoPokryte;
    }

    private static int poprawPokrycieSkończonym(String napis, boolean[] tab) {
        Scanner s = new Scanner(napis);
        int nowoPokryte = 0;
        int a = Integer.parseInt(s.next());
        int b = Integer.parseInt(s.next());
        int c = Integer.parseInt(s.next());

        for (int i = a; i <= Math.min(tab.length - 1, b); i += c) {
            if (!tab[i]) {
                nowoPokryte++;
            }
            tab[i] = true;
        }

        s.close();
        return nowoPokryte;
    }
}
