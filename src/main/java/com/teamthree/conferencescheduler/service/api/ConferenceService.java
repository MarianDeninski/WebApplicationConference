package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.dto.conference.CreateConferenceDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Venue;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public interface ConferenceService {

    Conference createNewConference(CreateConferenceDto dto, String owner);

    List<Conference> getAllConferences();

    Conference findConference(Long id);

    Conference editConference(long id, CreateConferenceDto dto);

    void deleteConferenceById(long id);

    List<Venue> getAllVenues();

    boolean checkIfThereIsOtherConferenceInVenueAtThatTime(CreateConferenceDto dto);

    boolean checkIfLoggedInUserIsOwner(UserDetails principal,Conference conference);

    Conference getById(long id);

    Conference getByName(String conferenceName);
}
