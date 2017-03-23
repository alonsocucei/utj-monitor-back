package models.entities;

import javax.persistence.Entity;
import models.Catalog;

/**
 *
 * @author alonsocucei
 */
@Entity
public class PEType extends Catalog {

    @Override
    public String toString() {
        return "PEType{" + super.toString() + "}";
    }
}