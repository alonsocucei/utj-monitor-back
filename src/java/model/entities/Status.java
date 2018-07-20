package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class Status extends BasicTable implements Cloneable {
    
    @Override
    public Status clone() throws CloneNotSupportedException {
        return (Status) super.clone();
    }
    
    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }
}
