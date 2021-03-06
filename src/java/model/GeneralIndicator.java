package model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import model.entities.Position;
import model.entities.ResetType;

/**
 *
 * @author alonsocucei
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public class GeneralIndicator extends AbstractIndicator implements Cloneable {
    private String baseYear;
    private String observations;
    private String source;
    private String link;
    private String formula;
    private String variables;
    private String method;
    private String metaDataObservations;
    private Set<Timestamp> resetDates;
    private ResetType resetType;
    private Position responsible;
    private List<Achievement> achievements;
    
    @Override
    protected GeneralIndicator clone() throws CloneNotSupportedException {
        GeneralIndicator result = (GeneralIndicator) super.clone();
        result.resetDates = new HashSet<>();
        result.achievements = new ArrayList<>();
        
        for (Timestamp timestamp: resetDates) {
            result.resetDates.add((Timestamp)timestamp.clone());
        }
        
        for (Achievement achievement: achievements) {
            result.achievements.add(achievement.clone());
        }
        
        return result;
    }

    public String getBaseYear() {
        return baseYear;
    }

    public void setBaseYear(String baseYear) {
        this.baseYear = baseYear;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    //TODO: add @Display annotation to source property
    //TODO: add @Graphic annotation to source resetDates property
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
    public Set<Timestamp> getResetDates() {
        return resetDates;
    }

    public void setResetDates(Set<Timestamp> resetDates) {
        this.resetDates = resetDates;
    }

    //TODO: add @Display annotation to resetType property 
    @OneToOne
    public ResetType getResetType() {
        return resetType;
    }

    public void setResetType(ResetType resetType) {
        this.resetType = resetType;
    }

    @OneToOne
    public Position getResponsible() {
        return responsible;
    }

    public void setResponsible(Position responsible) {
        this.responsible = responsible;
    }
    
    @ElementCollection
    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
    
    @Override
    public String toString() {
        List<String> resetDates = this.getResetDates().stream()
                .map(
                    t -> {
                        Instant instantDate = Instant.ofEpochMilli(t.getTime());
                        String instantString = instantDate.toString();
                        LocalDate localDate = LocalDate.parse(instantString.replaceAll("T.*", ""), DateTimeFormatter.ISO_DATE);
                        return "\"" + localDate + "\"";
                    }
                )
                .collect(Collectors.toList());
        String resetDatesString = resetDates.toString();
        resetDatesString = resetDates.size() > 0 ? resetDatesString.substring(1, resetDatesString.length() - 1) : "[]";
                
        return  super.toString() 
                + ", baseYear: " + "\"" + Objects.toString(getBaseYear(), "") + "\""
                + ", observations: " + "\"" + Objects.toString(getObservations(), "") + "\""
                + ", source: " + "\"" + Objects.toString(getSource(), "") + "\""
                + ", link: " + "\"" + Objects.toString(getLink(), "") + "\""
                + ", formula: " + "\"" + Objects.toString(getFormula(), "") + "\""
                + ", variables: " + "\"" + Objects.toString(getVariables(), "") + "\""
                + ", method: " + "\"" + Objects.toString(getMethod(), "") + "\""
                + ", metaDataObservations: " + "\"" + Objects.toString(getMetaDataObservations(), "") + "\""
                + ", resetDates: " + resetDatesString
                + ", resetType: " + Objects.toString(getResetType(), "\"\"")
                + ", responsible: " + Objects.toString(getResponsible(), "\"\"")
                + ", achievements: " + (getAchievements().size() > 0 ? getAchievements() : "[]");
    }
}
