package parser;

import java.util.Scanner;

import pokrycie.Dokładne;
import pokrycie.Naiwne;
import pokrycie.Pokrycie;
import pokrycie.Zachłanne;
import zbiory.Nieskończony;
import zbiory.Pojedynczy;
import zbiory.RodzinaZbiorów;
import zbiory.Skończony;
import zbiory.Zbiór;

/**
 * Odpowiada za wczytanie i przetworzenie danych z wejścia standardowego.
 * 
 * @author michal
 */
public class Wczytywanie {

    private Scanner wejście;
    private Scanner wiersz;
    private boolean czyKoniecWczytywania;

    public Wczytywanie() {
        wejście = new Scanner(System.in);
        if (wejście.hasNext()) {
            wiersz = new Scanner(wejście.next());
            czyKoniecWczytywania = false;
        } else {
            czyKoniecWczytywania = true;
        }
    }

    public void wczytaj() {
        int a = 0;
        RodzinaZbiorów r = new RodzinaZbiorów();

        while (!czyKoniecWczytywania) {
            a = kolejnaLiczba();

            if (a > 0) {
                wczytajZbiór(r, a);
            } else if (a < 0) {
                wczytajZapytanie(r, -a);
            } else {
                dodajPusty(r);
            }

            sprawdźCzyKoniecWejścia();
        }
    }

    private void wczytajZbiór(RodzinaZbiorów r, int a) {
        int b = 0, c = 0;
        Zbiór zb = new Zbiór();

        while (true) {
            b = kolejnaLiczba();
            if (b < 0) {
                c = kolejnaLiczba();
                if (c < 0) {
                    dodajSkończony(zb, a, -c, -b);
                    a = kolejnaLiczba();
                    if (a == 0) {
                        r.dodajZbiór(zb);
                        return;
                    }
                } else if (c > 0) {
                    dodajNieskończony(zb, a, -b);
                    a = c;
                } else {
                    dodajNieskończony(zb, a, -b);
                    r.dodajZbiór(zb);
                    return;
                }
            } else if (b > 0) {
                dodajJednoElementowy(zb, a);
                a = b;
            } else {
                dodajJednoElementowy(zb, a);
                r.dodajZbiór(zb);
                return;
            }
        }
    }

    private void wczytajZapytanie(RodzinaZbiorów r, int a) {
        int b = kolejnaLiczba();
        Pokrycie zapytanie;

        switch (b) {
        case 1:
            zapytanie = new Dokładne(a);
            zapytanie.czyPokrywa(r.getRodzina(), r.getIle());
            break;
        case 2:
            zapytanie = new Zachłanne(a);
            zapytanie.czyPokrywa(r.getRodzina(), r.getIle());
            break;
        case 3:
            zapytanie = new Naiwne(a);
            zapytanie.czyPokrywa(r.getRodzina(), r.getIle());
        }
    }

    private void dodajPusty(RodzinaZbiorów r) {
        Pojedynczy ele = new Pojedynczy(0);
        Zbiór pusty = new Zbiór();
        pusty.dodajElement(ele);
        r.dodajZbiór(pusty);
    }

    private void dodajSkończony(Zbiór r, int a, int b, int c) {
        Skończony ele = new Skończony(a, b, c);
        r.dodajElement(ele);
    }

    private void dodajNieskończony(Zbiór r, int a, int b) {
        Nieskończony ele = new Nieskończony(a, b);
        r.dodajElement(ele);
    }

    private void dodajJednoElementowy(Zbiór r, int a) {
        Pojedynczy ele = new Pojedynczy(a);
        r.dodajElement(ele);
    }

    private void sprawdźCzyKoniecWejścia() {
        if (!wiersz.hasNext() && !wejście.hasNext()) {
            czyKoniecWczytywania = true;
        }
    }

    private int kolejnaLiczba() {
        if (!wiersz.hasNext()) {
            wiersz = new Scanner(wejście.next());
        }

        return Integer.parseInt(wiersz.next());
    }
}
