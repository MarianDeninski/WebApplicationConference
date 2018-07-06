package com.teamthree.conferencescheduler.controllers.venue;

import com.teamthree.conferencescheduler.dto.venue.AddVenueDto;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;
import com.teamthree.conferencescheduler.service.api.UserService;
import com.teamthree.conferencescheduler.service.api.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.ADD_VENUE;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.ALL_VENUES;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/venue")
public class VenueController {

    private VenueService venueService;
    private UserService userService;

    @Autowired
    public VenueController(VenueService venueService, UserService userService) {
        this.venueService = venueService;
        this.userService = userService;
    }


    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addVenue(Model model) {

        model.addAttribute(VIEW, ADD_VENUE);
        return BASE_LAYOUT;
    }

    @GetMapping("/all")
    public String allVenue(Model model) {

        List<Venue> venues = this.venueService.getAllVenues();
        model.addAttribute("venues", venues);
        model.addAttribute(VIEW, ALL_VENUES);
        return BASE_LAYOUT;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String processVenue(Model model, AddVenueDto dto, Principal principal) {

        User user = this.userService.findByUsername(principal.getName());
        Venue venue = new Venue(dto.getAddress(), dto.getName(), user);

        try {
            this.venueService.addVenue(venue);
        } catch (ApplicationRuntimeException ex) {
            model.addAttribute(VIEW_MESSAGE, ex.getMessage());
            model.addAttribute(VIEW, ADD_VENUE);
            return BASE_LAYOUT;
        }

        return REDIRECT_TO_MY_PROFILE;
    }
}
