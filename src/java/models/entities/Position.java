/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import models.Employee;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class Position {
    private long id;
    private String name;
    private String description;
    private String email;
    private Position supervisor;
    private Employee player;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return (int) getId();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Position)) {
            return false;
        }
        
        Position other = (Position) object;
        
        if (this.id != other.id) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Position{id:" + id + ", name:" + getName() +
                ", description:" + getDescription() + ", email:" + getEmail() +
                ", supervisor:" + getSupervisor() + ", player:" + getPlayer() +
                "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne
    public Position getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Position supervisor) {
        this.supervisor = supervisor;
    }

    @Embedded
    public Employee getPlayer() {
        return player;
    }

    public void setPlayer(Employee player) {
        this.player = player;
    }
}
