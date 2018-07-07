package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.dto.session.SessionDto2;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.User;

import java.util.List;

public interface SessionService {

    SessionDto2 createSession(SessionDto dto);

    List<Conference> getAllConferencesOwnByUser(User user);

    boolean checkIfUserIsOwnerOfConference(Long id, User user);

    Session editSession(Long id, SessionDto dto);

    Session getById(Long id);

    void deleteById(Long id);

    Conference getConferenceById(long conferenceId);

    Session addSessionToHall(SessionDto dto, Conference conference);

    void addUserToSession(User user, Long sessionId);

    Conference getConferenceBySessionId(Long sessionId);

    List<Session> findByConference(Conference conference);

    List<Session> findByConferenceAndDate(Conference conference, String date);

    List<Session> findByName(String name);

}
