package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.entities.*;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.repositories.HallRepository;
import com.teamthree.conferencescheduler.repositories.SessionRepository;
import com.teamthree.conferencescheduler.repositories.SpeakerRepository;
import com.teamthree.conferencescheduler.service.api.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository;
    private SpeakerRepository speakerRepository;
    private ConferenceRepository conferenceRepository;
    private HallRepository hallRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, SpeakerRepository speakerRepository, ConferenceRepository conferenceRepository,
                              HallRepository hallRepository) {
        this.sessionRepository = sessionRepository;
        this.speakerRepository = speakerRepository;
        this.conferenceRepository = conferenceRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Session createSession(SessionDto dto) {
        Conference conference =conferenceRepository.findByName(dto.getConferenceName());

        Speaker  speaker = new Speaker(dto.getSpeakerName(),dto.getSpeakerDescription(),dto.getSpeakerPhoto());
        Session session = new Session(dto.getName(),dto.getDescription(),conference);

        //TODO find a way that save doesn't flush the data to the db, or just use AJAX and use 1 page for creating session

        this.sessionRepository.save(session);
        this.speakerRepository.save(speaker);

        speaker.setSession(session);
        session.setSpeaker(speaker);
        //TODO detach/merge/evict
        //EntityManager em =
        this.sessionRepository.saveAndFlush(session);
        this.speakerRepository.saveAndFlush(speaker);
        return  session;
    }

    @Override
    public List<Conference> getAllConferencesOwnByUser(User user) {
        List<Conference> allConferences = this.conferenceRepository.findAll();
        List<Conference> userConferences = new ArrayList<Conference>();

        for (Conference conference : allConferences) {
            if (conference.getOwner().getId() == user.getId()) {
                userConferences.add(conference);
            }
        }

        return userConferences;
    }

    @Override
    public boolean checkIfUserIsOwnerOfConference(Long id, User user) {
        Conference conference = this.conferenceRepository.getOne(id);
        if (conference.getOwner().getId() == user.getId()) {
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
        Speaker speaker = new Speaker(dto.getName(), dto.getSpeakerDescription(), dto.getSpeakerPhoto());
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

    @Override
    public Conference getConferenceById(long conferenceId) {
        return this.conferenceRepository.getOne(conferenceId);
    }

    @Override
    public Session addSessionToHall(SessionDto dto, Session session) {
        Hall hall = this.hallRepository.findByName(dto.getHall());
        if (this.checkIfHallIsTakenAtThatMoment(hall, dto)) {
            //Throw error on view to choose another day or another hour
        }

        session.setHall(hall);
        session.setDay(dto.getDay());
        session.setStartHour(dto.getStartHour());
        session.setEndHour(dto.getEndHour());
        sessionRepository.saveAndFlush(session);
        return session;
    }

    @Override
    public void addUserToSession(User user, Long sessionId) {
        Session session=this.sessionRepository.getOne(sessionId);
        session.getUsersGoing().add(user);
        sessionRepository.saveAndFlush(session);
    }

    @Override
    public Conference getConferenceBySessionId(Long sessionId) {
        Session session =  this.sessionRepository.findOne(sessionId);
        return session.getConference();
    }

    private boolean checkIfHallIsTakenAtThatMoment(Hall hall, SessionDto dto) {

        String targetSessionDay = dto.getDay();
        String targetSessionStartHour = dto.getStartHour();
        //String targetSessionEndHour = dto.getEndHour();

        //String targetSessionDuration = DateUtil.getTimeLapseOfSession(dto.getStartHour(),dto.getEndHour());

        String sessionInDbStartHour = "";
        String sessionInDbEndHour = "";
        //String sessionInDbDuration = "";

        //May be stupid , but it works
        for (Session session : hall.getSessions()) {
            if (session.getDay().equals(targetSessionDay)) {

                sessionInDbStartHour = session.getStartHour();
                sessionInDbEndHour = session.getEndHour();

                if (targetSessionStartHour.equals(sessionInDbStartHour)) {
                    return true;
                }

                int sessionInDbStartHourInt = Integer.parseInt(sessionInDbStartHour.split(":")[0]);
                int sessionInDbEndHourInt = Integer.parseInt(sessionInDbEndHour.split(":")[0]);

                int targetSessionStartHourInt = Integer.parseInt(targetSessionStartHour.split(":")[0]);

                for (int i = sessionInDbStartHourInt; i <= sessionInDbEndHourInt; i++) {
                    if (i == targetSessionStartHourInt) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
