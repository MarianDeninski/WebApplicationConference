package com.teamthree.conferencescheduler.dto.program_max;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ProgramMaximumDto implements Serializable {
    @NotNull
    private String conferenceName;

    @NotNull
    private String targetDate;

    public ProgramMaximumDto(String conferenceName, String targetDate) {
        this.conferenceName = conferenceName;
        this.targetDate = targetDate;
    }

    public String getConferenceName() {
        return this.conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getTargetDate() {
        return this.targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
}
