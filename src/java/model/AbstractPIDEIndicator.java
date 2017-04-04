/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import model.entities.StrategicItem;

/**
 *
 * @author alsanchez
 */
@MappedSuperclass
public abstract class AbstractPIDEIndicator extends GeneralIndicator {
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
        return super.toString();
    }
}
