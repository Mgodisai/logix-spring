package hu.alagi.logixspring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class MilestoneDelayDto {

    @NotNull
    private Long milestoneId;

    @NotNull
    @PositiveOrZero
    private Integer delayInMinutes;

    public MilestoneDelayDto(@NotNull Long milestoneId, @NotNull Integer delayInMinutes) {
        this.milestoneId = milestoneId;
        this.delayInMinutes = delayInMinutes;
    }


    @NotNull
    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(@NotNull Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    @NotNull
    public Integer getDelayInMinutes() {
        return delayInMinutes;
    }

    public void setDelayInMinutes(@NotNull Integer delayInMinutes) {
        this.delayInMinutes = delayInMinutes;
    }
}
