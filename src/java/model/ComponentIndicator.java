/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class ComponentIndicator {
    private String componentIndicatorName;
    private String componentIndicatorDescription;
    private List<ComponentIndicatorData> componentIndicatorData;

    @OneToMany
    public List<ComponentIndicatorData> getComponentIndicatorData() {
        return componentIndicatorData;
    }

    public void setComponentIndicatorData(List<ComponentIndicatorData> componentIndicatorData) {
        this.componentIndicatorData = componentIndicatorData;
    }

    public String getComponentIndicatorName() {
        return componentIndicatorName;
    }

    public void setComponentIndicatorName(String componentIndicatorName) {
        this.componentIndicatorName = componentIndicatorName;
    }

    public String getComponentIndicatorDescription() {
        return componentIndicatorDescription;
    }

    public void setComponentIndicatorDescription(String componentIndicatorDescription) {
        this.componentIndicatorDescription = componentIndicatorDescription;
    }
}