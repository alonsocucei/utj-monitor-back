package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class Periodicity extends BasicTable implements Cloneable {

    @Override
    public Periodicity clone() throws CloneNotSupportedException {
        return (Periodicity) super.clone();
    }
    
    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }
}
