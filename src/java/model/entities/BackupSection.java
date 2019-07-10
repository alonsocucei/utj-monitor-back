package model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import model.Section;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class BackupSection {
    private String jsonData;
    private Section section;
    private long id;
            
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Section getSection() {
        return section;
    }
    
    public void setSection(Section section) {
        this.section = section;
    }
    
    @Lob
    public String getJsonData() {
        return jsonData;
    }
    
    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
