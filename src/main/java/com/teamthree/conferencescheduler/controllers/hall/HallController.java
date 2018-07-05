package com.teamthree.conferencescheduler.controllers.hall;

import com.teamthree.conferencescheduler.dto.hall.AddHallDto;
import com.teamthree.conferencescheduler.entities.Hall;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.service.api.HallService;
import com.teamthree.conferencescheduler.service.api.UserService;
import com.teamthree.conferencescheduler.service.api.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/hall")
public class HallController {

    private UserService userService;
    private VenueService venueService;
    private HallService hallService;


    @Autowired
    public HallController(UserService userService, VenueService venueService, HallService hallService) {
        this.userService = userService;
        this.venueService = venueService;
        this.hallService = hallService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String getAddHall(Model model, Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        List<Venue> venues = this.venueService.getVenuesByOwner(user);

        model.addAttribute("venues", venues);
        model.addAttribute(VIEW, ADD_HALL);
        return BASE_LAYOUT;
    }


    @PostMapping("/add")
    public String addHall(Model model, AddHallDto dto) {

        Venue venue = this.venueService.getVenueByName(dto.getVenueName());
        Hall hall = new Hall(dto.getHallName(), dto.getSeatCapacity(), venue);
        this.hallService.createHall(hall);

        return REDIRECT_TO_MY_PROFILE;
    }
}
