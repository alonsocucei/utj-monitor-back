package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private List<Grade> grades;
    private StrategicItem strategicItem;
    private String potentialRisk;
    private String implementedActions;
    //MECASUT
    private ClassType classType;
    //PE
    private PE pe;
    private Indicator pideIndicator;
    private boolean isGlobal = false;
    
    @Override
    public Indicator clone() throws CloneNotSupportedException {
        Indicator result = (Indicator) super.clone();
        result.grades = new ArrayList<>();
        
        for (Grade grade: grades) {
            result.grades.add(grade.clone());
        }
        
        return result;
    }
        
    public boolean getIsGlobal() {
        return isGlobal;
    }
    
    public void setIsGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }
    
    @OneToOne
    public Indicator getPideIndicator() {
        return pideIndicator;
    }

    public void setPideIndicator(Indicator pideIndicator) {
        this.pideIndicator = pideIndicator;
    }
    
    @OneToOne
    public PE getPe() {
        return pe;
    }

    public void setPe(PE pe) {
        this.pe = pe;
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
        String gradesString = Objects.toString(this.getGrades());
        gradesString = this.getGrades() != null && this.getGrades().size() > 0 
                ? gradesString.substring(1, gradesString.length()) : "[]";
        
        return "{" + super.toString() 
                + ", grades: " + gradesString
                + ", strategicItem: " + Objects.toString(this.getStrategicItem(), "\"\"")
                + ", potentialRisk: " + "\"" + Objects.toString(this.getPotentialRisk(), "\"\"") + "\""
                + ", implementedActions: " + "\"" + Objects.toString(this.getImplementedActions(), "\"\"") + "\""
                + ", classType: " + Objects.toString(this.getClassType(), "\"\"")
                + ", pe: " + Objects.toString(this.getPe(), "\"\"")
                + ", pideIndicator: " + Objects.toString(this.getPideIndicator(), "\"\"")
                + "}";
    }
}
