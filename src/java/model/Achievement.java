/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import model.converters.LocalDateToDateConverter;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class Achievement {
    private double data;
    private LocalDate date;
    private AchievementType achievementType;
    
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
    @Convert(converter=LocalDateToDateConverter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AchievementType getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(AchievementType achievementType) {
        this.achievementType = achievementType;
    }
}
