/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import models.Catalog;

/**
 *
 * @author alsanchez
 */
@Entity
@Access(AccessType.PROPERTY)
public class Area extends Catalog {
    private AreaType type;
    
    @Override
    public String toString() {
        return "Area{" + super.toString() + ", type:" + getType() + "}";
    }

    @OneToOne
    public AreaType getType() {
        return type;
    }

    public void setType(AreaType type) {
        this.type = type;
    }
}
