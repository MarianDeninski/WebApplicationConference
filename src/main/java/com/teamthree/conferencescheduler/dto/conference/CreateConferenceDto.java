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
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private String venueName;

    public CreateConferenceDto(){

    }

    public CreateConferenceDto(String name,String description,String venueName,Date startDate,Date endDate){

        this.name=name;
        this.description=description;
        this.venueName=venueName;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getName() {
        return name;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }
}
