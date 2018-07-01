package com.teamthree.conferencescheduler.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "seat_capacity")
    private long seatCapacity;

    @ManyToOne()
    private Venue venue;

    @OneToMany(mappedBy = "hall")
    private List<Session> sessions;

    public Hall() {
        this.sessions = new ArrayList<>();
    }

    public Hall(String name, long seatCapacity, Venue venue, List<Session> sessions) {
        this.name = name;
        this.seatCapacity = seatCapacity;
        this.venue = venue;
        this.sessions = sessions;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSeatCapacity() {
        return this.seatCapacity;
    }

    public void setSeatCapacity(long seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Venue getVenue() {
        return this.venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Session> getSessions() {
        return this.sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
