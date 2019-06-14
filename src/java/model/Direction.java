package model;

/**
 *
 * @author alonsocucei
 */
public enum Direction {
    POSITIVE("Positivo"), NEGATIVE("Negativo");
    
    private final String name;
    
    private Direction(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "\"" + this.getName() + "\"";
    }
}
