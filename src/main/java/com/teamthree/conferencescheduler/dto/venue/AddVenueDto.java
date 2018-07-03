package com.teamthree.conferencescheduler.dto.venue;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AddVenueDto implements Serializable {

    @NotNull
    private String address;

    public AddVenueDto(String address){
        this.address=address;
    }

    public String getAddress() {
        return address;
    }
}
