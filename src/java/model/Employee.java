package model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

/**
 *
 * @author alonsocucei
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class Employee implements Cloneable {
    private String name;
    private Set<Phone> phones = new HashSet<>();
    
    @Override
    public Employee clone() throws CloneNotSupportedException {
        Employee result = (Employee) super.clone();
        result.phones = new HashSet<>();
        
        for (Phone phone: phones) {
            result.phones.add(phone.clone());
        }
        
        return result;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ElementCollection
    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }
    
    @Override
    public String toString() {
//        final StringBuilder phonesString = new StringBuilder("[");
//        
//        getPhones().stream()
//                .forEach(
//                        p -> {
//                            phonesString.append(p.toString() + ", ");
//                        }
//                );        
//        
//        phonesString.replace(phonesString.lastIndexOf(",", 0), phonesString.length(), "");
//        
//        if (phonesString.length() == 0) {
//            phonesString.append("[]");
//        }
        
        return "{"
                + "name: " + "\"" + this.getName() + "\""
                + ", phones: " + this.getPhones()
                + "}";
    }
}
