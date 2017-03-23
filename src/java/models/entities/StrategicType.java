/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities;

import javax.persistence.Entity;
import models.Catalog;

/**
 *
 * @author alonsocucei
 */
@Entity
public class StrategicType extends Catalog {
    
    @Override
    public String toString() {
        return "StrategicType{" + super.toString() + "}";
    }
}
