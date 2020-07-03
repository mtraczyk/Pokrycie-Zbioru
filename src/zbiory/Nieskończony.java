package zbiory;

/**
 * Klasa reprezentująca nieskończony element zbioru.
 * 
 * @author Michał Traczyk
 */
public class Nieskończony extends ElementZbioru {

    private final int początek;
    private final int różnica;

    public Nieskończony(int początek, int różnica) {
        this.początek = początek;
        this.różnica = różnica;
    }

    @Override
    public String toString() {
        return Integer.toString(początek) + " " + Integer.toString(różnica);
    }
}
