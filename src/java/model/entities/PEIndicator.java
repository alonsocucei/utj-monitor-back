package model.entities;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class PEIndicator extends Indicator {
    private String shortName;
    private PE pe;
    private List<Component> components;
    
    @OneToOne
    public PE getPe() {
        return pe;
    }

    public void setPe(PE pe) {
        this.pe = pe;
    }
    
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    @OneToMany
    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
    
    @Override
    public String toString() {
        return "PEIndicator{" + super.toString()
                + "}";
    }
}
