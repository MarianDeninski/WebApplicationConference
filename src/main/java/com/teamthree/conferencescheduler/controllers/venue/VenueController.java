package com.teamthree.conferencescheduler.controllers.venue;

import com.teamthree.conferencescheduler.dto.venue.AddVenueDto;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.repositories.VenueRepository;
import com.teamthree.conferencescheduler.service.api.VenueService;
import com.teamthree.conferencescheduler.service.impl.VenueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.ADD_VENUE;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.REDIRECT_TO_MY_PROFILE;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;

@Controller
@RequestMapping("/venue")
public class VenueController {

    private VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }


    @GetMapping("/add")
    //TODO: IT SHOULD NOT BE POSSIBLE TO ACCESS THIS PAGE WITHOUT LOGIN!
    public String addVenue(Model model) {

        model.addAttribute(VIEW, ADD_VENUE);
        return BASE_LAYOUT;
    }

    @PostMapping("/add")
    //TODO: IT SHOULD NOT BE POSSIBLE TO ACCESS THIS PAGE WITHOUT LOGIN!
    public String processVenue(AddVenueDto dto) {

        Venue venue = new Venue(dto.getName(), dto.getAddress());
        this.venueService.addVenue(venue);
        return REDIRECT_TO_MY_PROFILE;
    }
}
