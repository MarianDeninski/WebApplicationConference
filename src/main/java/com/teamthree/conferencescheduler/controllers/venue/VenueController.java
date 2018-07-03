package com.teamthree.conferencescheduler.controllers.venue;

import com.teamthree.conferencescheduler.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.ADD_VENUE;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;

@Controller
@RequestMapping("/venue")
public class VenueController {

    private VenueRepository venueRepository;

    @Autowired
    public VenueController(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }


    @GetMapping("/add")
    //TODO: IT SHOULD NOT BE POSSIBLE TO ACCESS THIS PAGE WITHOUT LOGIN!
    public String addVenue(Model model) {

        model.addAttribute(VIEW, ADD_VENUE);
        return BASE_LAYOUT;
    }
}
