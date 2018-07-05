package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.service.api.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    ConferenceRepository conferenceRepository;

    @Autowired
    public HomeServiceImpl(ConferenceRepository conferenceRepository){
        this.conferenceRepository=conferenceRepository;
    }

    @Override
    public List<Conference> getAllConference() {
        return this.conferenceRepository.findAll();
    }


}
