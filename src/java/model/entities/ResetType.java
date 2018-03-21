package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class ResetType extends BasicTable implements Cloneable {
    
    @Override
    public ResetType clone() throws CloneNotSupportedException {
        return (ResetType) super.clone();
    }
    
    @Override
    public String toString() {
        return "ResetType{" + super.toString() + "}";
    }
}
