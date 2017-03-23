package models.entities;

import javax.persistence.Entity;
import models.Catalog;

/**
 *
 * @author alonsocucei
 */
@Entity
public class MeasureUnitType extends Catalog {

    @Override
    public String toString() {
        return "MeasureUnitType{" + super.toString() + "}";
    }
}
