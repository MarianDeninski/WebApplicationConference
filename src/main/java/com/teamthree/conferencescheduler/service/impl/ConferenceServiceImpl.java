package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.service.api.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceServiceImpl(ConferenceRepository conferenceRepository){
        this.conferenceRepository = conferenceRepository;
    }

    public void createNewConference(Conference conference){
        this.conferenceRepository.saveAndFlush(conference);
    }

    @Override
    public List<Conference> getAllConferences() {
        return this.conferenceRepository.findAll();
    }

}
