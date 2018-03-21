package model;

import javax.persistence.Embeddable;

/**
 *
 * @author alonsocucei
 */
@Embeddable
public class Grade implements Cloneable {
    private String color;
    private int maxPercentage;

    @Override
    public Grade clone() throws CloneNotSupportedException {
        return (Grade) super.clone();
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(int maxPercentage) {
        this.maxPercentage = maxPercentage;
    }
}
