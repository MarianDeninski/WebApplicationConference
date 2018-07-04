package com.teamthree.conferencescheduler.dto.hall;

import javax.validation.constraints.NotNull;

public class AddHallDto {

    @NotNull
    private String venueName;

    @NotNull
    private String hallName;

    @NotNull
    private long seatCapacity;

    public AddHallDto() {

    }

    public AddHallDto(String venueName, String hallName, long seatCapacity) {
        this.venueName = venueName;
        this.hallName = hallName;
        this.seatCapacity = seatCapacity;
    }


    public String getVenueName() {
        return this.venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getHallName() {
        return this.hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public long getSeatCapacity() {
        return this.seatCapacity;
    }

    public void setSeatCapacity(long seatCapacity) {
        this.seatCapacity = seatCapacity;
    }
}
