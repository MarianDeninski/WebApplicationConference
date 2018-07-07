package com.teamthree.conferencescheduler.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_hour")
    private String startHour;

    @Column(name = "end_hour")
    private String endHour;

    @OneToOne(mappedBy = "session", targetEntity = Speaker.class)
    private Speaker speaker;
    @ManyToOne()
    private Hall hall;
    @ManyToOne()
    private Conference conference;
    @ManyToMany(mappedBy = "userSessions")
    private List<User> usersGoing;
    private String day;
    public Session(String name, String description, String startHour, String endHour, Hall hall, Conference conference, String day) {
        this.name = name;
        this.description = description;
        this.startHour = startHour;
        this.endHour = endHour;
        this.hall = hall;
        this.conference = conference;
        this.usersGoing = new ArrayList<User>();
        this.day = day;
    }

    public Session() {
    }

    public Session(String name, String description, Conference conference) {
        this.name = name;
        this.description = description;
        this.conference = conference;
        this.usersGoing = new ArrayList<User>();

    }

    public List<User> getUsersGoing() {
        return usersGoing;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getId() {
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

    public void setDescription(String description) {
        this.description = description;
    }

    public Conference getConference() {
        return conference;
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

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
