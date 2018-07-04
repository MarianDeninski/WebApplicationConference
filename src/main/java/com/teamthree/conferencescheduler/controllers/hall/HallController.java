package com.teamthree.conferencescheduler.controllers.hall;

import com.teamthree.conferencescheduler.dto.hall.AddHallDto;
import com.teamthree.conferencescheduler.entities.Hall;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.repositories.HallRepository;
import com.teamthree.conferencescheduler.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/hall")
public class HallController {

    VenueRepository venueRepository;
    HallRepository hallRepository;
    @Autowired
    public HallController(VenueRepository venueRepository){
        this.venueRepository=venueRepository;
    }
    @RequestMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String getAddHall(Model model){
        model.addAttribute(VIEW,ADD_HALL);
        return BASE_LAYOUT;
    }
    @PostMapping("/add")
    public String addHall(Model model, AddHallDto dto){

        Venue venue =venueRepository.findByAddress(dto.getVenueAddress());
        if(venue==null){
            venue = new Venue(dto.getVenueAddress(),new ArrayList<Hall>());
            venueRepository.saveAndFlush(venue);
        }
        Hall hall = new Hall(dto.getName(),dto.getSeatCapacity(),venue,new ArrayList<Session>());
        venue.getHalls().add(hall);
        //TODO za mirkata
        //TODO -> NEXT -> add session to hall
    return null;

    }
}
