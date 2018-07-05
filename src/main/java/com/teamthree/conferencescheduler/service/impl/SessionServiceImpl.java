package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.Speaker;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.repositories.SessionRepository;
import com.teamthree.conferencescheduler.repositories.SpeakerRepository;
import com.teamthree.conferencescheduler.service.api.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl  implements SessionService {
    private SessionRepository sessionRepository;
    private SpeakerRepository speakerRepository;
    private ConferenceRepository conferenceRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository,SpeakerRepository speakerRepository,ConferenceRepository conferenceRepository) {
        this.sessionRepository = sessionRepository;
        this.speakerRepository = speakerRepository;
        this.conferenceRepository=conferenceRepository;
    }

    @Override
    public Session createSession(SessionDto dto) {
        //TODO TO BE TESTED WHEN HTML VIEW IS READY
        Conference conference =conferenceRepository.findByName(dto.getSpeakerName());
        if(conference==null){
            //throw error to the view for not found conference with this name
        }
        Speaker  speaker = new Speaker(dto.getSpeakerName(),dto.getSpeakerDescription(),dto.getSpeakerPhoto());
        Session session = new Session(dto.getName(),dto.getDescription(),dto.getStartHour(),dto.getEndHour(),speaker,conference);

        this.sessionRepository.saveAndFlush(session);

        return  session;
    }
}
