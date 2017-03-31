/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author alonsocucei
 */
@MappedSuperclass
@Embeddable
@Access(AccessType.PROPERTY)
public abstract class ComponentData {
//    private List<Achievement<Month>> progressData;
//
//    @ElementCollection
//    public List<Achievement<Month>> getProgressData() {
//        return progressData;
//    }
//
//    public void setProgressData(List<Achievement<Month>> progressData) {
//        this.progressData = progressData;
//    }
    private Achievement progressData;
    
//    public ComponentData() {
//        setProgressData(new ArrayList<>());
//    }
    
//    @ElementCollection
    @Embedded
    public Achievement getProgressData() {
        return progressData;
    }

    public void setProgressData(Achievement progressData) {
        this.progressData = progressData;
    }
}