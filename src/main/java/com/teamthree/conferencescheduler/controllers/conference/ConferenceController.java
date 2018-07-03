package com.teamthree.conferencescheduler.controllers.conference;

import com.teamthree.conferencescheduler.dto.conference.CreateConferenceDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.repositories.UserRepository;
import com.teamthree.conferencescheduler.repositories.VenueRepository;
import com.teamthree.conferencescheduler.service.api.RoleService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.teamthree.conferencescheduler.constants.views.ViewConstants;

import javax.jws.WebParam;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.CREATE_CONFERENCE;

@Controller
@RequestMapping("/conference")
public class ConferenceController {

    private UserService userService;
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private VenueRepository venueRepository;
    @Autowired
    public ConferenceController(UserService userService, UserRepository userRepository,ConferenceRepository conferenceRepository,VenueRepository venueRepository){
        this.userService = userService;
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.venueRepository = venueRepository;
    }

    //Get all conferences
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllConferences(Model model){
        return null;
    }



    //Get createConference view
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateConference(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if ((auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();
            return "redirect:/user/login";
        }
        model.addAttribute("view",CREATE_CONFERENCE);
        return BASE_LAYOUT ;
    }

    //Post data to db
    @PostMapping(path = "/create")
    public String createConference(Model model, CreateConferenceDto dto){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();

            //Add button on view to create Venue if venue doesn't exist
            Venue venue = venueRepository.findByName(dto.getVenueName());
            Conference conference = new Conference(dto.getName(),dto.getDescription(),venue,dto.getStartDate(),dto.getEndDate());
            conferenceRepository.saveAndFlush(conference);
            return "redirect:/conference/details{id}";
        }
        return "redirect:/users/login";



    }

    @RequestMapping(value = " /conference/{id}", method=RequestMethod.GET)
    public String details(@PathVariable long id){
        //TODO implement Conference detail
        return null;
    }

}
