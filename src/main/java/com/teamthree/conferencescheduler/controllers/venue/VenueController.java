package com.teamthree.conferencescheduler.controllers.venue;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.teamthree.conferencescheduler.app_utils.UserUtil;
import com.teamthree.conferencescheduler.dto.venue.AddVenueDto;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;
import com.teamthree.conferencescheduler.repositories.VenueRepository;
import com.teamthree.conferencescheduler.service.api.UserService;
import com.teamthree.conferencescheduler.service.api.VenueService;
import com.teamthree.conferencescheduler.service.impl.VenueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.ADD_VENUE;
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
    //TODO: IT SHOULD NOT BE POSSIBLE TO ACCESS THIS PAGE WITHOUT LOGIN!
    public String addVenue(Model model) {

        model.addAttribute(VIEW, ADD_VENUE);
        return BASE_LAYOUT;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    //TODO: IT SHOULD NOT BE POSSIBLE TO ACCESS THIS PAGE WITHOUT LOGIN!
    public String processVenue(Model model, AddVenueDto dto, Principal principal) {

        // USER WILL NEVER BE NULL IF HE IS CURRENTLY LOGGED IN, NO NEED TO CHECK!
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
