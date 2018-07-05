package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.entities.Hall;
import com.teamthree.conferencescheduler.repositories.HallRepository;
import com.teamthree.conferencescheduler.service.api.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallServiceImpl implements HallService {

    private HallRepository hallRepository;

    @Autowired
    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public void createHall(Hall hall) {
        this.hallRepository.saveAndFlush(hall);
    }

    @Override
    public List<Hall> findByVenueName(String venueName) {
        return this.hallRepository.findByVenueIsLike(venueName);
    }
}
