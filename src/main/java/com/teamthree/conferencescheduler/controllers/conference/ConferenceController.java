package com.teamthree.conferencescheduler.controllers.conference;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.teamthree.conferencescheduler.dto.conference.CreateConferenceDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.repositories.ConferenceRepository;
import com.teamthree.conferencescheduler.repositories.UserRepository;
import com.teamthree.conferencescheduler.repositories.VenueRepository;
import com.teamthree.conferencescheduler.service.api.ConferenceService;
import com.teamthree.conferencescheduler.service.api.RoleService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import java.util.ArrayList;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/conference")
public class ConferenceController {

    private UserService userService;
    private UserRepository userRepository;
    private VenueRepository venueRepository;
    private ConferenceService conferenceService;

    @Autowired
    public ConferenceController(UserService userService, UserRepository userRepository,ConferenceRepository conferenceRepository,VenueRepository venueRepository,ConferenceService conferenceService){
        this.userService = userService;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
        this.conferenceService = conferenceService;

    }

    //Get all conferences
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllConferences(Model model){

        ArrayList<Conference >conferences= (ArrayList<Conference>) conferenceService.getAllConferences();

        model.addAttribute("conferences",conferences);
        model.addAttribute(VIEW,ALL_CONFERENCES);

        return BASE_LAYOUT;

    }

    @RequestMapping(value = " /remove/{id}", method=RequestMethod.POST)
    public String removeConference(@PathVariable long id){
        //TODO Implement method
        return null;
    }

    //GET edit view html
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getEditConference(@PathVariable long id){
        //TODO Implement method
        return null;
    }

    //POST edit conference
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public  String editConference(@PathVariable long id){
        //TODO Implement method
        return null;
    }

    //Get createConference view
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateConference(Model model){
        model.addAttribute("view",CREATE_CONFERENCE);
        return BASE_LAYOUT ;
    }

    //Post data to db
    @PostMapping(path = "/create")
    @PreAuthorize("isAuthenticated()")
    public String createConference(Model model, CreateConferenceDto dto){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            String currentUserName = auth.getName();

            User owner = userRepository.findByUsername(currentUserName);

            //Add button on view to create Venue if venue doesn't exist
            Venue venue = venueRepository.findByAddress(dto.getVenueAddress());

           // if(venue.getAddress()==null){
           // }

            Conference conference = new Conference(dto.getName(),dto.getDescription(),venue,dto.getStartDate(),dto.getEndDate(),owner, new ArrayList<Session>());
            this.conferenceService.createNewConference(conference);

            model.addAttribute("conference",conference);
            model.addAttribute(VIEW,CONFERENCE_DETAILS);
            return BASE_LAYOUT;

    }

    @RequestMapping(value = "/details/{id}", method=RequestMethod.GET)
    public String details(@PathVariable long id,Model model){
        Conference conference = conferenceRepository.findOne(id);
        model.addAttribute(VIEW,CONFERENCE_DETAILS);
        model.addAttribute("conference",conference);
        return BASE_LAYOUT;
    }

}
