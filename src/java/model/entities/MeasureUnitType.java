package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class MeasureUnitType extends BasicTable {

    @Override
    public String toString() {
        return "MeasureUnitType{" + super.toString() + "}";
    }
}
