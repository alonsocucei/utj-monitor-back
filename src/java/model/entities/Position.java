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
import javax.persistence.OneToOne;
import model.BasicTable;
import model.Employee;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class Position extends BasicTable {
    private String description;
    private String email;
    private Position supervisor;
    private Employee player;
    private Area area;
    
    @Override
    public String toString() {
        return "Position{" + super.toString() +
                ", description:" + getDescription() + ", email:" + getEmail() +
                ", supervisor:" + getSupervisor() + ", player:" + getPlayer() +
                "}";
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
    
    @OneToOne
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
