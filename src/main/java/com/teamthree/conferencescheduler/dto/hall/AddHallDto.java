package com.teamthree.conferencescheduler.dto.hall;

import com.teamthree.conferencescheduler.entities.Venue;

import javax.validation.constraints.NotNull;

public class AddHallDto {

    @NotNull
    private Venue venue;

    @NotNull
    private String name;

    @NotNull
    private long seatCapacity;

    public AddHallDto() {

    }

    public AddHallDto(Venue venue, String name, long seatCapacity) {
        this.venue = venue;
        this.name = name;
        this.seatCapacity = seatCapacity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSeatCapacity() {
        return seatCapacity;
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
}
