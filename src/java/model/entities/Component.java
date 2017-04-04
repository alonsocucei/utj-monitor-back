/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import model.AbstractComponent;
import model.ComponentIndicator;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class Component extends AbstractComponent {
    private String componentDescription;
    private String justification;
    private ComponentIndicator indicator;

    @Override
    public String toString() {
        return "Component{" + super.toString() + ", description: " + getComponentDescription()
                + ", justification:" + getJustification() + "}";
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

    public String getComponentDescription() {
        return componentDescription;
    }

    public void setComponentDescription(String componentDescription) {
        this.componentDescription = componentDescription;
    }
}