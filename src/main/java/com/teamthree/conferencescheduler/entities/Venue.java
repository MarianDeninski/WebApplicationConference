package com.teamthree.conferencescheduler.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "venue")
    private List<Hall> halls;

    @OneToMany(mappedBy = "venue")
    private List<Conference> conferences;

    public Venue() {
        this.halls = new ArrayList<>();
    }

    public Venue(String address, List<Hall> halls) {
        this.address = address;
        this.halls = halls;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Conference> getConferences() {
        return this.conferences;
    }

    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }
}
