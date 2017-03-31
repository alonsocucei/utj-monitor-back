/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import model.AbstractComponent;
import model.ComponentIndicator;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class Component extends AbstractComponent {
    private String description;
    private String justification;
    private ComponentIndicator indicator;

    @Override
    public String toString() {
        return "Component{" + super.toString() + ", description: " + getDescription()
                + ", justification:" + getJustification() + "}";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    @Embedded
    public ComponentIndicator getIndicator() {
        return indicator;
    }

    public void setIndicator(ComponentIndicator indicator) {
        this.indicator = indicator;
    }
}