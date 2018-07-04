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
        Venue venue = venueRepository.findByName(dto.getVenue().getName());
//        if(venue==null) {
//
//           // venue = new Venue(dto.getVenueAddress());
//        }
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

    @Override
    public void editConference(long id, CreateConferenceDto dto) {
        Conference conference = this.findConference(id);
        conference.setName(dto.getName());
        conference.setDescription(dto.getDescription());
        conference.setEndDate(dto.getEndDate());
        Venue venue = venueRepository.findByName(dto.getVenue().getName());
        if(venue==null){
            //TODO add  venue name to the CreateConference dto so new venue can be inicializes
        }
       // conference.setVenue(newVenue);
        //TODO add new session

    }

    @Override
    public void deleteConferenceById(long id) {
        this.userRepository.delete(id);
    }

    @Override
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

}
