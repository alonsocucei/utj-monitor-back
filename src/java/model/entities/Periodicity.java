package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class Periodicity extends BasicTable {

    @Override
    public String toString() {
        return "Periodicity{" + super.toString() + "}";
    }
}
