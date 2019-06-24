package model.entities;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import model.BasicTable;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class Backup extends BasicTable {
    private List<BackupSection> sections;
    
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    public List<BackupSection> getSections() {
        return sections;
    }
    
    public void setSections(List<BackupSection> sections) {
        this.sections = sections;
    }
}
