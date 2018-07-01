package com.teamthree.conferencescheduler.entities;

import javax.persistence.*;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    private Set<Role> roles;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Transient
    private List<Conferences> conferencesList;

    public User() {
    }

    public User(String username, String fullName, String password) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
    }

    public User(String username, Set<Role> roles, String password, List<Conferences> conferencesList) {
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

    public List<Conferences> getConferencesList() {
        return this.conferencesList;
    }

    public void setConferencesList(List<Conferences> conferencesList) {
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

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
