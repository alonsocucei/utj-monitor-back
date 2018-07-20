package model;

import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author alonsocucei
 */
@Embeddable
public class Phone implements Cloneable {
    private String number;
    private PhoneType type;

    @Override
    public Phone clone() throws CloneNotSupportedException {
        return (Phone) super.clone();
    }
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "{"
                + "number: " + "\"" + this.getNumber() + "\""
                + ", type: " + "\"" + Objects.toString(this.getType(), "\"\"") + "\""
                + "}";
    }
}
