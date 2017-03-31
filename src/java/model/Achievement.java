/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class Achievement /*<T extends TemporalAccessor>*/ {
    private double data;
//    private T temporal;
    private AchievementType achievementType;
    
//    public Achievement() {}
//    
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

    //TODO: Add converter here
//    public T getTemporal() {
//        return temporal;
//    }
//
//    public void setTemporal(T temporal) {
//        this.temporal = temporal;
//    }

    public AchievementType getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(AchievementType achievementType) {
        this.achievementType = achievementType;
    }
}
