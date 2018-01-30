package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class Status extends BasicTable {
    @Override
    public String toString() {
        return "Status{" + super.toString() + "}";
    }
}
