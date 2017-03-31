/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import model.entities.Position;

/**
 *
 * @author alonsocucei
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class AbstractComponent extends BasicTable {
    private MeasureUnit measureUnit;
    private Position responsible;
    
    @Embedded
    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    @OneToOne
    public Position getResponsible() {
        return responsible;
    }

    public void setResponsible(Position responsible) {
        this.responsible = responsible;
    }
    
    @Override
    public String toString() {
        return "" + super.toString() + "";
    }
}
