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
    private String componentDescription;
    private String justification;
    private ComponentIndicator componentIndicator;

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    @Embedded
    public ComponentIndicator getComponentIndicator() {
        return componentIndicator;
    }

    public void setComponentIndicator(ComponentIndicator componentIndicator) {
        this.componentIndicator = componentIndicator;
    }

    public String getComponentDescription() {
        return componentDescription;
    }

    public void setComponentDescription(String componentDescription) {
        this.componentDescription = componentDescription;
    }
    
    @Override
    public String toString() {
        return "{" + super.toString()
                + ", description: " + getComponentDescription()
                + ", justification: " + getJustification()
                + ", indicator: " + getComponentIndicator()
                + "}";
    }
}