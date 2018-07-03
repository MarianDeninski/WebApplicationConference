package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.dto.conference.CreateConferenceDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.repositories.UserRepository;
import com.teamthree.conferencescheduler.repositories.VenueRepository;
import com.teamthree.conferencescheduler.service.api.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;
    private UserRepository userRepository;
    private VenueRepository venueRepository;

    @Autowired
    public ConferenceServiceImpl(ConferenceRepository conferenceRepository,UserRepository userRepository,VenueRepository venueRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public Conference createNewConference(CreateConferenceDto dto, String owner) {
        User ownerClass = this.userRepository.findByUsername(owner);
        Venue venue = venueRepository.findByAddress(dto.getVenueAddress());
        Conference conference = new Conference(dto.getName(),dto.getDescription(),venue,dto.getStartDate(),dto.getEndDate(),ownerClass,new ArrayList<Session>());
        return conference;
    }

    @Override
    public List<Conference> getAllConferences() {
        return this.conferenceRepository.findAll();
    }

    @Override
    public Conference findConference(Long id) {
        return this.conferenceRepository.findById(id);
    }

}
