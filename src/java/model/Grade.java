/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Embeddable;

/**
 *
 * @author alonsocucei
 */
@Embeddable
public class Grade {
    private String color;
    private int maxPercentage;

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
