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
public class Naiwne extends Pokrycie {

    public Naiwne(int koniec) {
        super(koniec);
    }

    @Override
    public void czyPokrywa(Zbiór[] rodzina, int moc) {
        boolean[] tab = new boolean[getKoniec() + 1];
        StringBuilder s = new StringBuilder();

        for (int i = 0; i <= getKoniec(); i++) {
            tab[i] = false;
        }

        for (int i = 0; i < moc; i++) {
            if (sprawdźCzyZbiórPoprawia(rodzina[i], tab)) {
                if (s.length() != 0) {
                    s.append(" ");
                }
                s.append((i + 1));
            }
        }

        wypiszWynik(tab, s);
    }

    private void wypiszWynik(boolean[] tab, StringBuilder s) {
        boolean czyPokrył = true;

        for (int i = 1; i <= getKoniec(); i++) {
            if (!tab[i]) {
                czyPokrył = false;
            }
        }

        if (!czyPokrył) {
            System.out.println("0");
        } else {
            System.out.println(s);
        }
    }

    private boolean sprawdźCzyZbiórPoprawia(Zbiór zbiór, boolean[] tab) {
        ElementZbioru[] zb = zbiór.getZbiór();
        boolean czyPoprawia = false;
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
                czyPoprawia = sprawdźCzyPojedynczyPoprawia(napis, tab, czyPoprawia);
                break;
            case 1:
                czyPoprawia = sprawdźCzyNieskończonyPoprawia(napis, tab, czyPoprawia);
                break;
            case 2:
                czyPoprawia = sprawdźCzySkończonyPoprawia(napis, tab, czyPoprawia);
            }
        }

        return czyPoprawia;
    }

    private static boolean sprawdźCzyPojedynczyPoprawia(String napis, boolean[] tab,
            boolean czyPoprawia) {
        int a = Integer.parseInt(napis);
        if (a >= 1 && a < tab.length && !tab[a]) {
            czyPoprawia = true;
            tab[a] = true;
        }

        return czyPoprawia;
    }

    private static boolean sprawdźCzyNieskończonyPoprawia(String napis, boolean[] tab,
            boolean czyPoprawia) {
        Scanner s = new Scanner(napis);
        int a = Integer.parseInt(s.next());
        int b = Integer.parseInt(s.next());

        for (int i = a; i < tab.length; i += b) {
            if (!tab[i]) {
                czyPoprawia = true;
                tab[i] = true;
            }
        }

        s.close();
        return czyPoprawia;
    }

    private static boolean sprawdźCzySkończonyPoprawia(String napis, boolean[] tab,
            boolean czyPoprawia) {
        Scanner s = new Scanner(napis);
        int a = Integer.parseInt(s.next());
        int b = Integer.parseInt(s.next());
        int c = Integer.parseInt(s.next());

        for (int i = a; i <= Math.min(tab.length - 1, b); i += c) {
            if (!tab[i]) {
                czyPoprawia = true;
                tab[i] = true;
            }
        }

        s.close();
        return czyPoprawia;
    }
}
