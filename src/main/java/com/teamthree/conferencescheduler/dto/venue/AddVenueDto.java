package com.teamthree.conferencescheduler.dto.venue;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AddVenueDto implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String address;

    public AddVenueDto() {
    }

    public AddVenueDto(String name, String address) {
        this.name = name;
        this.address = address;
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
}
