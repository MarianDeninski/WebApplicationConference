package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.Hall;

import java.util.List;

public interface HallService {

    void createHall(Hall hall);

    List<Hall> findByVenueName(String venueName);
}
