package model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class Area extends BasicTable implements Cloneable {
    
    @Override
    public Area clone() throws CloneNotSupportedException {
        return (Area) super.clone();
    }
    
    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }
}
