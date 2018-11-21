/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author alonsocucei
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class BasicTable implements Cloneable{
    private long id;
    private String name;
    
    @Override
    protected BasicTable clone() throws CloneNotSupportedException {
        return (BasicTable) super.clone();
    }
    
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * @return the name
     */
    @Column(nullable=false)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return (int) getId();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BasicTable)) {
            return false;
        }
        
        BasicTable other = (BasicTable) object;
        
        return this.getId() == other.getId();
    }

    @Override
    public String toString() {
        return "id: " + getId() 
                + ", name: " + "\"" + getName() + "\"";
    }
}
