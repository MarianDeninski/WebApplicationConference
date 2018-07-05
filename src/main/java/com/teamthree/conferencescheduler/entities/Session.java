package com.teamthree.conferencescheduler.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_Date")
    private String startHour;

    @Column(name = "end_hour")
    private String endHour;

    @OneToOne(mappedBy = "session", targetEntity = Speaker.class)
    private Speaker speaker;

    @ManyToOne()
    private Hall hall;

    @ManyToOne()
    private Conference conference;

    @NotNull
    private String day;
    public Session() {
    }

    public Session( String name, String description, String startHour, String endHour, Speaker speaker, Conference conference,String day) {
        this.name = name;
        this.description = description;
        this.startHour = startHour;
        this.endHour = endHour;
        this.speaker = speaker;
        this.conference = conference;
        this.day= day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Conference getConference() {
        return conference;
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

    public Speaker getSpeaker() {
        return this.speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public Hall getHall() {
        return hall;
    }
}
