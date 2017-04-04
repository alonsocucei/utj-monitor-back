/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class ComponentIndicator extends AbstractIndicator {
//    private List<ComponentIndicatorData> data;
//
//    @ElementCollection
//    public List<ComponentIndicatorData> getData() {
//        return data;
//    }
//
//    public void setData(List<ComponentIndicatorData> data) {
//        this.data = data;
//    }
}