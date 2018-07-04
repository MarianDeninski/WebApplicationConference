package com.teamthree.conferencescheduler.dto.conference;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class CreateConferenceDto implements Serializable {


    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

    @NotNull
    private String venueName;

    public CreateConferenceDto(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public CreateConferenceDto(String name, String description, String startDate, String endDate, String venueName) {

        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venueName = venueName;
    }
}
