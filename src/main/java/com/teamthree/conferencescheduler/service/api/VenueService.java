package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.entities.Venue;

import java.util.List;

public interface VenueService {
    void addVenue(Venue venue);

    Venue getVenueById(long id);

    List<Venue> getAllVenues();

    Venue getVenueByName(String name);

    void delete(long id);

    List<Venue> getVenuesByOwner(User user);
}
