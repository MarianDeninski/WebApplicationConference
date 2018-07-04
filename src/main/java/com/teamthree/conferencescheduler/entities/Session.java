package com.teamthree.conferencescheduler.entities;

import javax.persistence.*;
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

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @OneToOne(mappedBy = "session", targetEntity = Speaker.class)
    private Speaker speaker;

    @ManyToOne()
    private Hall hall;

    @ManyToOne()
    private Conference conference;


    public Session() {
    }

    public Session(String name, String description, String startDate, String endDate, Speaker speaker) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.speaker = speaker;
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

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
