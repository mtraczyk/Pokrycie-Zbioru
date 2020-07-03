package zbiory;

/**
 * Klasa reprezentująca pojedynczy element zbioru.
 * 
 * @author Michał Traczyk
 */
public class Pojedynczy extends ElementZbioru {

    private final int element;

    public Pojedynczy(int element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return Integer.toString(element);
    }
}
