package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.dto.conference.CreateConferenceDto;
import com.teamthree.conferencescheduler.entities.Conference;

import java.util.List;

public interface ConferenceService {

    Conference createNewConference(CreateConferenceDto dto,String owner);

    List<Conference> getAllConferences();

    Conference findConference(Long id);

    void editConference(long id, CreateConferenceDto dto);

    void deleteConferenceById(long id);
}
