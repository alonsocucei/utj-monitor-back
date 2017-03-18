package models.forms;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Id;

/**
 * @author alonsocucei
 */
//@Entity
public class Catalog implements Serializable {
    private static int catalogCounter = 0;
    private static int entriesCounter = 0;
    @Id
    private int id;
    private String name;
    private String description;
    private Set<CatalogEntry> entries;
    
    public Catalog(String name, String description) {
        this.id = ++catalogCounter;
        this.name = name;
        this.description = description;
        entries = new HashSet<>();
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getName() {
        return name;
    }
    
    public Set<CatalogEntry> getEntries() {
        return entries;
    }
    
//    @Entity
    public class CatalogEntry implements Serializable {
        @Id
        private int id;
        private int value;
        private String label;
        
        public CatalogEntry(int value, String label) {
            this.id = ++entriesCounter;
            this.value = value;
            this.label = label;
        }
        
        public boolean equals(Object o) {
            if (o != null && o instanceof CatalogEntry) {
                CatalogEntry entry = (CatalogEntry) o;
                
                return entry.getValue() == this.getValue();
            }
            
            return false;
        }
        
        public int getValue() {
            return value;
        }
        
        public String getLabel() {
            return label;
        }
        
        public int getId() {
            return id;
        }
    }
    
    public boolean addEntry(int value, String label) {
        CatalogEntry entry = new CatalogEntry(value, label);
        
        if (entries.contains(entry)) {
            entry = null;
            return false;
        } else {
            entries.add(entry);
            return true;
        }
    }
}