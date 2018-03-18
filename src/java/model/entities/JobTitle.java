package model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class JobTitle extends BasicTable {
    @Override
    public String toString() {
        return "JobTitle{" + super.toString() + "}";
    }
}
