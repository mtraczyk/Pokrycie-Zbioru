package zbiory;

/**
 * Klasa reprezentująca skończony element zbioru.
 * 
 * @author Michał Traczyk
 */
public class Skończony extends ElementZbioru {

    private final int początek;
    private final int koniec;
    private final int różnica;

    public Skończony(int początek, int koniec, int różnica) {
        this.początek = początek;
        this.koniec = koniec;
        this.różnica = różnica;
    }

    @Override
    public String toString() {
        return Integer.toString(początek) + " " + Integer.toString(koniec) + " "
                + Integer.toString(różnica);
    }
}
