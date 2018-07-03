package com.teamthree.conferencescheduler.controllers.venue;

import com.teamthree.conferencescheduler.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/venue")
public class VenueController {

    private VenueRepository venueRepository;

    @Autowired
    public VenueController(VenueRepository venueRepository){
        this.venueRepository=venueRepository;
    }
    //TODO Implement controller

    @RequestMapping("/add")
    public String addVenue(){
        return null;
    }
}
