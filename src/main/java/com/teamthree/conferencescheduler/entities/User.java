package com.teamthree.conferencescheduler.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    @OneToMany(mappedBy = "owner")
    private List<Venue> venues;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    private Set<Role> roles;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Conference> conferencesList;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_session",
            joinColumns = { @JoinColumn(name = "user_id",nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "session_id") }
    )
    private List<Session> userSessions;


    public List<Session> getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(List<Session> userSessions) {
        this.userSessions = userSessions;
    }

    public User() {
        this.conferencesList = new ArrayList<>();
    }

    public User(String username, String fullName, String password) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.conferencesList = new ArrayList<>();
        this.roles = new HashSet<>();
        this.userSessions = new ArrayList<>();
    }

    public User(String username, Set<Role> roles, String password, List<Conference> conferencesList) {
        this.username = username;
        this.roles = roles;
        this.password = password;
        this.conferencesList = conferencesList;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Conference> getConferencesList() {
        return this.conferencesList;
    }

    public void setConferencesList(List<Conference> conferencesList) {
        this.conferencesList = conferencesList;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return this.id;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }


    public List<Venue> getVenues() {
        return this.venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }
}
