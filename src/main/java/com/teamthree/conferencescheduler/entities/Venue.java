package com.teamthree.conferencescheduler.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
<<<<<<< HEAD
@Table(name="venues")
=======
@Table(name = "venues")
>>>>>>> 2ccc06989bd09739d896c5aa0c61caf3e3ae0585
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
     private Long id;

    public Venue() {
        this.halls = new ArrayList<Hall>();

    }

    public Venue( String address, List<Hall> halls) {
        this.address = address;
        this.halls = halls;
    }
=======
    private Long id;
>>>>>>> 2ccc06989bd09739d896c5aa0c61caf3e3ae0585

    @Column(name = "address")
    private String address;

<<<<<<< HEAD
    @Column(name ="halls")
    @OneToMany(mappedBy = "hall")
=======
    @Transient
>>>>>>> 2ccc06989bd09739d896c5aa0c61caf3e3ae0585
    private List<Hall> halls;

    public Venue() {
        this.halls = new ArrayList<>();
    }

    public Venue(String address, List<Hall> halls) {
        this.address = address;
        this.halls = halls;
    }

    public List<Hall> getHalls() {
        return this.halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
