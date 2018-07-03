package com.teamthree.conferencescheduler.dto.hall;

import javax.validation.constraints.NotNull;

public class AddHallDto {

    @NotNull
    private  String venueAddress;

    @NotNull
    private  String name;

    @NotNull
    private long seatCapacity;

    public AddHallDto(String venueAddress, String name, long seatCapacity) {
        this.venueAddress = venueAddress;
        this.name = name;
        this.seatCapacity = seatCapacity;
    }

    public AddHallDto() {

    }

    public String getName() {
        return name;
    }

    public long getSeatCapacity() {
        return seatCapacity;
    }

    public String getVenueAddress() {
        return venueAddress;
    }


}
