package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.Conference;

import java.util.List;

public interface ConferenceService {

    void createNewConference(Conference conference);

    List<Conference> getAllConferences();

    Conference findConference(Long id);
}
