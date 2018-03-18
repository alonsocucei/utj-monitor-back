package model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import model.Employee;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class Position {
    private long id;
    private String email;
    private Employee player;
    private Area area;
    private JobTitle jobTitle;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        return (int) getId();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Position)) {
            return false;
        }
        
        Position other = (Position) object;
        
        return this.getId() == other.getId();
    }
    
    @Override
    public String toString() {
        return "Position{" + super.toString() +
                ", email:" + getEmail() +
                ", player:" + getPlayer() + ", area:" + getArea() +
                ", jobTitle:" + getJobTitle() + 
                "}";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Embedded
    public Employee getPlayer() {
        return player;
    }

    public void setPlayer(Employee player) {
        this.player = player;
    }
    
    @ManyToOne
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
    @ManyToOne
    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }
}
