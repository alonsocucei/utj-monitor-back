/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import model.AbstractPIDEIndicator;
import model.Grade;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class PIDEIndicator extends AbstractPIDEIndicator {
    private List<Grade> grades;
    private StrategicItem strategicItem;
    
    @ElementCollection
    //TODO: Add Graphic annotation
    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
    
    @OneToOne
    public StrategicItem getStrategicItem() {
        return strategicItem;
    }

    public void setStrategicItem(StrategicItem strategicItem) {
        this.strategicItem = strategicItem;
    }
    
    @Override
    public String toString() {
        return "PIDEIndicator{" + super.toString() + "}";
    }
}
