/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
