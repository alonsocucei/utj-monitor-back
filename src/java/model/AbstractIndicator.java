/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.entities.IndicatorType;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import model.entities.Periodicity;
import model.entities.Status;

/**
 *
 * @author alonsocucei
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public class AbstractIndicator extends BasicTable {
    private String description;
    private Direction direction = Direction.POSITIVE;
    private IndicatorType indicatorType;
    private Periodicity periodicity;
    private MeasureUnit measureUnit;
    private Status status;
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Embedded
    //TODO: add graphic annotation
    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    //TODO: add graphic annotation
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //TODO: add graphic annotation
    public IndicatorType getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }
    
    @OneToOne
    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }
    
    @Override
    public String toString() {
        return "id: " + getId()
                + ", name: " + getName()
                + ", description: " + getDescription()
                + ", direction: " + getDirection()
                + ", type: " + getIndicatorType()
                + ", periodicity: " + getPeriodicity()
                + ", unit: " + getMeasureUnit()
                + ", status: " + getStatus();
    }
}
