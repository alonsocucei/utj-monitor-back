package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class StrategicType extends BasicTable implements Cloneable {
    
    @Override
    public StrategicType clone() throws CloneNotSupportedException {
        return (StrategicType) super.clone();
    }
    
    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }
}
