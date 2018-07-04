package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;
import com.teamthree.conferencescheduler.repositories.VenueRepository;
import com.teamthree.conferencescheduler.service.api.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.teamthree.conferencescheduler.constants.errors.ErrorHandlingConstants.VENUE_ALREADY_EXISTS;
import static com.teamthree.conferencescheduler.constants.errors.ErrorHandlingConstants.VENUE_CANNOT_BE_NULL;

@Service
public class VenueServiceImpl implements VenueService {

    private VenueRepository venueRepository;

    @Autowired
    public VenueServiceImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }


    @Override
    public void addVenue(Venue venue) {
        if (venue == null) {
            throw new ApplicationRuntimeException(VENUE_CANNOT_BE_NULL);
        }

        if (this.checkIfExists(venue.getName())) {
            throw new ApplicationRuntimeException(VENUE_ALREADY_EXISTS);
        }

        this.venueRepository.saveAndFlush(venue);
    }

    @Override
    public Venue getVenueById(long id) {
        return this.venueRepository.findById(id);
    }

    @Override
    public List<Venue> getAllVenues() {
        return this.venueRepository.findAll();
    }

    @Override
    public Venue getVenueByName(String name) {
        return this.venueRepository.findByName(name);
    }

    private boolean checkIfExists(String venueName) {
        return this.venueRepository.findByName(venueName) != null;
    }
}
