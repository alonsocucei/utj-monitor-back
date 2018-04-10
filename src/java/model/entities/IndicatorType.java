package model.entities;

import javax.persistence.Entity;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class IndicatorType extends BasicTable {
    @Override
    public String toString() {
        return "IndicatorType{" + super.toString() + "}";
    }
//    PIDE, MECASUT, PROGRAMA_EDUCATIVO, COMPONENTE
}
