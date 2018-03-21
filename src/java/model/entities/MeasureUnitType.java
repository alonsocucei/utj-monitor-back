package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class MeasureUnitType extends BasicTable implements Cloneable {
    
    @Override
    public MeasureUnitType clone() throws CloneNotSupportedException {
        return (MeasureUnitType) super.clone();
    }
    
    @Override
    public String toString() {
        return "MeasureUnitType{" + super.toString() + "}";
    }
}
