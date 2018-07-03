package com.teamthree.conferencescheduler.controllers.conference;

import com.sun.org.apache.xpath.internal.operations.Mod;
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

import java.util.ArrayList;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.ALL_CONFERENCES;
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
        ArrayList<Conference> conferences= (ArrayList<Conference>) conferenceRepository.findAll();

        // I have no idea if this will work
        model.addAttribute(conferences);
        model.addAttribute("view",ALL_CONFERENCES);

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

        if ((auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();

            //Add button on view to create Venue if venue doesn't exist
            Venue venue = venueRepository.findByAddress(dto.getVenueAddress());
            Conference conference = new Conference(dto.getName(),dto.getDescription(),venue,dto.getStartDate(),dto.getEndDate());
            conferenceRepository.saveAndFlush(conference);
            return "redirect:/conference/details{id}";
        }
        return "redirect:/users/login";



    }

    @RequestMapping(value = " /detail/{id}", method=RequestMethod.GET)
    public String details(@PathVariable long id){
        //TODO implement Conference detail
        Conference conference = conferenceRepository.findOne(id);

        return null;
    }

}
