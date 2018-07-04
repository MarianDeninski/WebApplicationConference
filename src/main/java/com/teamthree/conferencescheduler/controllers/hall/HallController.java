package com.teamthree.conferencescheduler.controllers.hall;

import com.teamthree.conferencescheduler.dto.hall.AddHallDto;
import com.teamthree.conferencescheduler.entities.Hall;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.repositories.HallRepository;
import com.teamthree.conferencescheduler.repositories.VenueRepository;
import com.teamthree.conferencescheduler.service.api.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/hall")
public class HallController {

    private VenueService venueService;
    private HallRepository hallRepository;

    @Autowired
    public HallController(VenueService venueService, HallRepository hallRepository) {
        this.venueService = venueService;
        this.hallRepository = hallRepository;
    }
<<<<<<< HEAD
    @RequestMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String getAddHall(Model model){
        model.addAttribute(VIEW,ADD_HALL);
        return BASE_LAYOUT;
    }
=======


    @GetMapping("/add")
//    @PreAuthorize("isAuthenticated()")
    public String getAddHall(Model model) {

        List<Venue> venues = this.venueService.getAllVenues();
        model.addAttribute("venues", venues);
        model.addAttribute(VIEW, ADD_HALL);
        return BASE_LAYOUT;
    }


>>>>>>> e09f391ffbde20d20abf28e7670a878e5a35dd48
    @PostMapping("/add")
    public String addHall(Model model, AddHallDto dto) {
        Venue venue = this.venueService.getVenueByName(dto.getVenueName());



        return null;
    }
}
