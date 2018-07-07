package com.teamthree.conferencescheduler.dto.session;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Hall;
import com.teamthree.conferencescheduler.entities.Speaker;

public  class SessionDto2 {

    private Conference conference;
 private Speaker speaker;
    private  String description;
    private  String name;
    private Hall hall;
    private String startHour;
    private String endHour;
    private String day;

    public SessionDto2() {
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
