/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import model.entities.MeasureUnitType;
import model.entities.Unit;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class MeasureUnit implements Cloneable {
    private MeasureUnitType type;
    private Unit unit;
    
    
    @Override
    public MeasureUnit clone() throws CloneNotSupportedException {
        return (MeasureUnit) super.clone();
    }
    
    @OneToOne
    public MeasureUnitType getType() {
        return type;
    }

    public void setType(MeasureUnitType type) {
        this.type = type;
    }

    @OneToOne
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof MeasureUnit)) {
            return false;
        }
        
        MeasureUnit measureUnit = (MeasureUnit) o;
        
        return getType() == measureUnit.getType() && getUnit() == measureUnit.getUnit();
    }
    
    @Override
    public int hashCode() {
        return getType().hashCode() ^ getUnit().hashCode();
    }

    @Override
    public String toString() {
        return "{"
                + "type:" + Objects.toString(getType(), "\"\"")
                + ", unit:" + Objects.toString(getUnit(), "\"\"")
                + "}";
    }
}
