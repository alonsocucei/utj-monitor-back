/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class Achievement {
    private double data;
    private Date temporal;
    private AchievementType type;

    //TODO: add graphic annotation
    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }

    @Temporal(TemporalType.DATE)
    public Date getTemporal() {
        return temporal;
    }

    public void setTemporal(Date temporal) {
        this.temporal = temporal;
    }

    public AchievementType getType() {
        return type;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }
}
