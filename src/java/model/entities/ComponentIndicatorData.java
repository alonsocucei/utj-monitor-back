/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import model.Achievement;
import model.AchievementType;

/**
 *
 * @author alonsocucei
 */
@Entity
@Access(AccessType.PROPERTY)
public class ComponentIndicatorData extends ComponentData {
    private Achievement initialProgress = new Achievement(AchievementType.PROGRESS);
    private Achievement finalGoal = new Achievement(AchievementType.GOAL);
            
    @Embedded
    @AttributeOverrides(
            {
                @AttributeOverride(
                        name = "data",
                        column = @Column(name = "INITIAL_PROGRESS_DATA")
                ),
                @AttributeOverride(
                        name = "date",
                        column = @Column(name = "INITIAL_PROGRESS_DATE")
                ),
                @AttributeOverride(
                        name = "achievementType",
                        column = @Column(name = "INITIAL_PROGRESS_ACHIEVEMENT_TYPE")
                )
    }
    )
    public Achievement getInitialProgress() {
        return initialProgress;
    }

    public void setInitialProgress(Achievement initialProgress) {
        this.initialProgress = initialProgress;
    }

    @Embedded
    @AttributeOverrides(
            {
                @AttributeOverride(
                        name = "data",
                        column = @Column(name = "FINAL_GOAL_DATA")
                ),
                @AttributeOverride(
                        name = "date",
                        column = @Column(name = "FINAL_GOAL_DATE")
                ),
                @AttributeOverride(
                        name = "achievementType",
                        column = @Column(name = "FINAL_GOAL_ACHIEVEMENT_TYPE")
                )
            }
    )
    public Achievement getFinalGoal() {
        return finalGoal;
    }

    public void setFinalGoal(Achievement finalGoal) {
        this.finalGoal = finalGoal;
    }
}
