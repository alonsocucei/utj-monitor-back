package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class Unit extends BasicTable implements Cloneable {
    
    @Override
    public Unit clone() throws CloneNotSupportedException {
        return (Unit) super.clone();
    }
    
    @Override
    public String toString() {
        return "Unit{" + super.toString() + "}";
    }
}
