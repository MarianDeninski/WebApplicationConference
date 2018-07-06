package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.Conference;

import java.util.List;
import java.util.Map;

public interface HomeService {

    List<Conference> getAllConference();

    Map<String, List<Conference>> splitByPastUpcomingActive(List<Conference> allConferences);
}
