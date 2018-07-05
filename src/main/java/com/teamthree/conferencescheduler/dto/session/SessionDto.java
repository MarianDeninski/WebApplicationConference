package com.teamthree.conferencescheduler.dto.session;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SessionDto implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String startHour;

    @NotNull
    private String endHour;

    @NotNull
    private String speakerName;

    @NotNull
    private String speakerDescription;

    @NotNull
    private String speakerPhoto;

    @NotNull
    private String conferenceName;


    public SessionDto(String name, String description, String startHour, String endHour, String speakerName, String speakerDescription, String speakerPhoto,String conferenceName) {

        this.name = name;
        this.description = description;
        this.startHour = startHour;
        this.endHour = endHour;
        this.speakerName = speakerName;
        this.speakerDescription = speakerDescription;
        this.speakerPhoto = speakerPhoto;
        this.conferenceName = conferenceName;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public SessionDto() {
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

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerDescription() {
        return speakerDescription;
    }

    public void setSpeakerDescription(String speakerDescription) {
        this.speakerDescription = speakerDescription;
    }

    public String getSpeakerPhoto() {
        return speakerPhoto;
    }

    public void setSpeakerPhoto(String speakerPhoto) {
        this.speakerPhoto = speakerPhoto;
    }

}
