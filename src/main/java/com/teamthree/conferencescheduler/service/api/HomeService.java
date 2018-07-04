package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.Conference;

import java.util.List;

public interface HomeService {

    List<Conference> getAllConference();
}
