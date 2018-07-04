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

    private ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {

        this.conferenceService = conferenceService;

    }

    //Get all conferences
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllConferences(Model model) {

        ArrayList<Conference> conferences = (ArrayList<Conference>) conferenceService.getAllConferences();

        model.addAttribute("conferences", conferences);
        model.addAttribute(VIEW, ALL_CONFERENCES);

        return BASE_LAYOUT;

    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = " /remove/{id}", method = RequestMethod.POST)
    public String removeConference(@PathVariable long id) {
        //TODO Implement method
        return null;
    }

    //GET edit view html
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getEditConference(@PathVariable long id,Model model) {
        model.addAttribute(VIEW,CONFERENCE_EDIT);
        return BASE_LAYOUT;
    }

    //POST edit conference
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editConference(@PathVariable long id,CreateConferenceDto dto) {
        Conference conference = conferenceService.findConference(id);
        //model.addAttribute(VIEW, CONFERENCE_DETAILS);
        //model.addAttribute("conference", conference);
        return BASE_LAYOUT;
    }

    //Get createConference view
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateConference(Model model) {
        model.addAttribute("view", CREATE_CONFERENCE);
        return BASE_LAYOUT;
    }

    //Post data to db
    @PostMapping(path = "/create")
    @PreAuthorize("isAuthenticated()")
    public String createConference(Model model, CreateConferenceDto dto) {
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();

        Conference conference = this.conferenceService.createNewConference(dto, owner);

        model.addAttribute("conference", conference);
        model.addAttribute(VIEW, CONFERENCE_DETAILS);

        return BASE_LAYOUT;

    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String details(@PathVariable long id, Model model) {
        Conference conference = conferenceService.findConference(id);
        model.addAttribute(VIEW, CONFERENCE_DETAILS);
        model.addAttribute("conference", conference);
        return BASE_LAYOUT;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable long id,Model model){
        conferenceService.deleteConferenceById(id);
        return "redirect:/conferences/all";
    }
}
