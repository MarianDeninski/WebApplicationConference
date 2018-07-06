package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.app_utils.DateUtil;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.service.api.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    ConferenceRepository conferenceRepository;

    @Autowired
    public HomeServiceImpl(ConferenceRepository conferenceRepository){
        this.conferenceRepository=conferenceRepository;
    }

    @Override
    public List<Conference> getAllConference() {
        return this.conferenceRepository.findAll();
    }

    @Override
    public Map<String, List<Conference>> splitByPastUpcomingActive(List<Conference> allConferences) {
        Map<String, List<Conference>> result = new HashMap<>();

        result.put("past", new ArrayList<>());
        result.put("active", new ArrayList<>());
        result.put("upcoming", new ArrayList<>());

        String today = DateUtil.getCurrentDateAsString();

        for (Conference conference : allConferences) {
            if (DateUtil.comparatorByStringDates(today, conference.getStartDate()) < 0) {
                result.get("past").add(conference);
            } else if (DateUtil.comparatorByStringDates(today, conference.getEndDate()) > 0) {
                result.get("upcoming").add(conference);
            } else {
                result.get("active").add(conference);
            }
        }

        return result;
    }
}
