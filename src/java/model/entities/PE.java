package model.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
public class PE extends BasicTable {
    private String shortName;
    private PEType type;
    
    @OneToOne
    public PEType getType() {
        return type;
    }
    
    public void setType(PEType type) {
        this.type = type;
    }
    
    public String getShortName() {
        return shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    @Override
    public String toString() {
        return "PE{" + super.toString() 
                + ", shortName: "
                + getShortName()
                + ", type: "
                + getType()
                + "}";
    }
}