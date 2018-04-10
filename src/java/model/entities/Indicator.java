package model.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import model.GeneralIndicator;
import model.Grade;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name="UTJIndicator")
public class Indicator extends GeneralIndicator implements Cloneable {
    private ClassType classType;
    private List<Grade> grades;
    private StrategicItem strategicItem;
    private String potentialRisk;
    private String implementedActions;
    
    @Override
    public Indicator clone() throws CloneNotSupportedException {
        Indicator result = (Indicator) super.clone();
        result.grades = new ArrayList<>();
        
        for (Grade grade: grades) {
            result.grades.add(grade.clone());
        }
        
        return result;
    }
    
    @OneToOne
    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }
    
    public void setImplementedActions(String implementedActions) {
        this.implementedActions = implementedActions;
    }
    
    public String getImplementedActions() {
        return implementedActions;
    }
    
    public void setPotentialRisk(String potentialRisk) {
        this.potentialRisk = potentialRisk;
    }
    
    public String getPotentialRisk() {
        return potentialRisk;
    }
    
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
        return "Indicator{" + super.toString() + "}";
    }
}
