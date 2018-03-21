/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class StrategicItem extends BasicTable implements Cloneable {
    private List<StrategicItem> children;
    private StrategicType strategicType;
    
    @Override
    public StrategicItem clone() throws CloneNotSupportedException {
        StrategicItem result = (StrategicItem) super.clone();
        result.children = new ArrayList<>();
        
        for (StrategicItem item: children) {
            result.children.add(item.clone());
        }
        
        return result;
    }
    
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    public List<StrategicItem> getChildren() {
        return children;
    }

    public void setChildren(List<StrategicItem> children) {
        this.children = children;
    }
    
    public boolean addChild(StrategicItem child) {
        return children.add(child);
    }
    
    public boolean removeChild(StrategicItem child) {
        return children.remove(child);
    }
    
    @OneToOne
    //TODO: Add Graphic annotation
    public StrategicType getStrategicType() {
        return strategicType;
    }

    public void setStrategicType(StrategicType strategicType) {
        this.strategicType = strategicType;
    }
    
    @Override
    public String toString() {
        return "StrategicItem{" + super.toString() 
                + ", children: " + getChildren()
                + ", strategicType: " + getStrategicType()
                + "}";
    }
}
