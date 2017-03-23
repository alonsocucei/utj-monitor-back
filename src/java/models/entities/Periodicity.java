package models.entities;

import javax.persistence.Entity;
import models.Catalog;

/**
 *
 * @author alonsocucei
 */
@Entity
public class Periodicity extends Catalog {

    @Override
    public String toString() {
        return "Periodicity{" + super.toString() + "}";
    }
}
