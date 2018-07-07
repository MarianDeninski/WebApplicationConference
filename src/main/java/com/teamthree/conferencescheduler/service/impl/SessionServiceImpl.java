package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.dto.session.SessionDto2;
import com.teamthree.conferencescheduler.entities.*;
import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;
import com.teamthree.conferencescheduler.repositories.*;
import com.teamthree.conferencescheduler.service.api.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.teamthree.conferencescheduler.constants.errors.ErrorHandlingConstants.HALL_IS_TAKEN;

@Service
public class SessionServiceImpl implements SessionService {
    private static SessionDto2 sessionDto2;
    private SessionRepository sessionRepository;
    private SpeakerRepository speakerRepository;
    private ConferenceRepository conferenceRepository;
    private HallRepository hallRepository;
    private UserRepository userRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, SpeakerRepository speakerRepository, ConferenceRepository conferenceRepository,
                              HallRepository hallRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.speakerRepository = speakerRepository;
        this.conferenceRepository = conferenceRepository;
        this.hallRepository = hallRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SessionDto2 createSession(SessionDto dto) {
        Conference conference = conferenceRepository.findByName(dto.getConferenceName());

        Speaker speaker = new Speaker(dto.getSpeakerName(), dto.getSpeakerDescription(), dto.getSpeakerPhoto());
        //Session session = new Session(dto.getName(), dto.getDescription(), conference);
        sessionDto2 = new SessionDto2();
        sessionDto2.setName(dto.getName());
        sessionDto2.setDescription(dto.getDescription());
        sessionDto2.setConference(conference);
        sessionDto2.setSpeaker(speaker);

        //this.sessionRepository.save(session);
        //this.speakerRepository.save(speaker);

        //speaker.setSession(session);
        //session.setSpeaker(speaker);
        //this.sessionRepository.saveAndFlush(session);
        //this.speakerRepository.saveAndFlush(speaker);
        return sessionDto2;
    }

    @Override
    public Session addSessionToHall(SessionDto dto) {
        Hall hall = this.hallRepository.findByName(dto.getHall());
        Session session = new Session(sessionDto2.getName(), sessionDto2.getDescription(), dto.getStartHour(), dto.getEndHour(), hall, sessionDto2.getConference(), dto.getDay());
        if (this.checkIfHallIsTakenAtThatMoment(hall, dto)) {
            throw new ApplicationRuntimeException(HALL_IS_TAKEN);
        }
        Speaker speaker = sessionDto2.getSpeaker();
        this.sessionRepository.save(session);
        this.speakerRepository.save(speaker);

        session.setSpeaker(speaker);
        speaker.setSession(session);

        // session.setHall(hall);
        //session.setDay(dto.getDay());
        //session.setStartHour(dto.getStartHour());
        //session.setEndHour(dto.getEndHour());
        speakerRepository.saveAndFlush(speaker);
        sessionRepository.saveAndFlush(session);
        sessionDto2 = new SessionDto2();
        return session;
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
    public void addUserToSession(User user, Long sessionId) {
        Session session = this.sessionRepository.getOne(sessionId);
        session.getUsersGoing().add(user);
        user.getUserSessions().add(session);
        sessionRepository.saveAndFlush(session);
        userRepository.saveAndFlush(user);
    }

    @Override
    public Conference getConferenceBySessionId(Long sessionId) {
        Session session = this.sessionRepository.findOne(sessionId);
        return session.getConference();
    }

    @Override
    public List<Session> findByConference(Conference conference) {
        if (conference == null) {
            throw new ApplicationRuntimeException("Conference cannot be null.");
        }

        return this.sessionRepository.findByConference(conference);
    }

    @Override
    public List<Session> findByConferenceAndDate(Conference conference, String date) {
        return this.sessionRepository.findByConferenceAndDay(conference, date);
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
