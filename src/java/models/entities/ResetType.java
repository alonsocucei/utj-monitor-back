package models.entities;

import javax.persistence.Entity;
import models.Catalog;

/**
 *
 * @author alonsocucei
 */
@Entity
public class ResetType extends Catalog {

    @Override
    public String toString() {
        return "ResetType{" + super.toString() + "}";
    }
}
