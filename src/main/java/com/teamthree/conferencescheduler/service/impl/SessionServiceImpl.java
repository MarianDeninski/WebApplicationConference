package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.Speaker;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.repositories.SessionRepository;
import com.teamthree.conferencescheduler.repositories.SpeakerRepository;
import com.teamthree.conferencescheduler.repositories.UserRepository;
import com.teamthree.conferencescheduler.service.api.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Conference conference =conferenceRepository.findByName(dto.getConferenceName());
        if(conference==null){
            //throw error to the view for not found conference with this name
        }
        Speaker  speaker = new Speaker(dto.getSpeakerName(),dto.getSpeakerDescription(),dto.getSpeakerPhoto());
        this.speakerRepository.saveAndFlush(speaker);
        Session session = new Session(dto.getName(),dto.getDescription(),dto.getStartHour(),dto.getEndHour(),speaker,conference);

        this.sessionRepository.saveAndFlush(session);

        return  session;
    }

    @Override
    public List<Conference> getAllConferencesOwnByUser(User user) {
        List<Conference> allConferences=this.conferenceRepository.findAll();
        List<Conference> userConferences = new ArrayList<Conference>();
        for (Conference conference : allConferences) {
            if(conference.getOwner().getId() == user.getId()){
                userConferences.add(conference);
            }
        }
        return userConferences;
    }

    @Override
    public boolean checkIfUserIsOwnerOfConference(Long id, User user) {
        Conference conference =this.conferenceRepository.getOne(id);
        if(conference.getOwner().getId() == user.getId()){
            return true;
        }
        return false;
    }

    @Override
    public Session editSession(Long id, SessionDto dto) {
        Session session = this.sessionRepository.findOne(id);
        session.setDescription(dto.getDescription());
        session.setStartHour(dto.getStartHour());
        session.setName(dto.getName());
        session.setEndHour(dto.getEndHour());
        Speaker speaker = new Speaker(dto.getName(),dto.getSpeakerDescription(),dto.getSpeakerPhoto());
        session.setSpeaker(speaker);
        this.sessionRepository.saveAndFlush(session);
        return session;
    }

    @Override
    public Session getById(Long id) {
        Session session = this.sessionRepository.findOne(id);
        return session;
    }

    @Override
    public void deleteById(Long id) {
        this.sessionRepository.delete(id);
    }
}
