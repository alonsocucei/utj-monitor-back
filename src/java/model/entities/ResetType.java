package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class ResetType extends BasicTable {

    @Override
    public String toString() {
        return "ResetType{" + super.toString() + "}";
    }
}
