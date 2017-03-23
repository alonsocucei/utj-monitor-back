/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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
}
