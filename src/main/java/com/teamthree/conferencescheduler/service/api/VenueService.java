package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.Venue;

public interface VenueService {
    void addVenue(Venue venue);

    Venue getVenueById(long id);
}
