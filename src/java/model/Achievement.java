package model;

import java.sql.Timestamp;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class Achievement implements Cloneable {
    private double data;
    private Timestamp date;
    private AchievementType achievementType;
    
    @Override
    public Achievement clone() throws CloneNotSupportedException {
        Achievement result = (Achievement) super.clone();
        result.date = (Timestamp) date.clone();
        
        return result;
    }
    
    public Achievement() {}
    
    public Achievement(AchievementType achievementType) {
        setAchievementType(achievementType);
    }
    
    //TODO: add graphic annotation
    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
    
    //TODO: add Graphic annotation
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public AchievementType getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(AchievementType achievementType) {
        this.achievementType = achievementType;
    }
}
