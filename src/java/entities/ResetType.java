package entities;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author alsanchez
 */
@Entity
@Access(AccessType.PROPERTY)
public class ResetType implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResetType)) {
            return false;
        }
        ResetType other = (ResetType) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ResetType[ id=" + id + " ]";
    }

    @Column(nullable=false, updatable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
