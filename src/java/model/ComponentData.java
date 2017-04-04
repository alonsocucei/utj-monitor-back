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
//@Embeddable
//@Access(AccessType.PROPERTY)
public class ComponentData {
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
    private List<Achievement> progressData;
    
//    public ComponentData() {
//        setProgressData(new ArrayList<>());
//    }
    
    @ElementCollection
    public List<Achievement> getProgressData() {
        return progressData;
    }

    public void setProgressData(List<Achievement> progressData) {
        this.progressData = progressData;
    }
}