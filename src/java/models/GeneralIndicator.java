/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.time.Year;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author alonsocucei
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class GeneralIndicator extends AbstractIndicator {
    private Year baseYear;
    private String observations;
    private String source;
    private String link;
    private String formula;
    private String variables;
    private String method;
    private String metaDataObservations;
    private Set<LocalDate> resetDates;
    
    //TODO: add @Display annotation to source property
    //TODO: add @Graphic annotation to source resetDates property

    public Year getBaseYear() {
        return baseYear;
    }

    public void setBaseYear(Year baseYear) {
        this.baseYear = baseYear;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMetaDataObservations() {
        return metaDataObservations;
    }

    public void setMetaDataObservations(String metaDataObservations) {
        this.metaDataObservations = metaDataObservations;
    }

    @ElementCollection
    public Set<LocalDate> getResetDates() {
        return resetDates;
    }

    public void setResetDates(Set<LocalDate> resetDates) {
        this.resetDates = resetDates;
    }
}
