package com.teamthree.conferencescheduler.controllers.conference;

import com.teamthree.conferencescheduler.app_utils.DateUtil;
import com.teamthree.conferencescheduler.dto.conference.CreateConferenceDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;
import com.teamthree.conferencescheduler.service.api.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/conference")
public class ConferenceController {

    private ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {

        this.conferenceService = conferenceService;

    }

    //Get createConference view
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateConference(Model model) {
        ArrayList<Venue> venues = (ArrayList<Venue>) this.conferenceService.getAllVenues();
        model.addAttribute("venues", venues);
        model.addAttribute("view", CREATE_CONFERENCE);
        return BASE_LAYOUT;
    }

    //Post data to db
    @PostMapping(path = "/create")
    @PreAuthorize("isAuthenticated()")
    public String createConference(CreateConferenceDto dto, Model model) {

        //Check if there is other conference in these days
        boolean checkIfThereIsOtherConferenceInVenue = this.conferenceService.checkIfThereIsOtherConferenceInVenueAtThatTime(dto);
        if (checkIfThereIsOtherConferenceInVenue) {
            //Should not be able to add another conference
        }

        String owner = SecurityContextHolder.getContext().getAuthentication().getName();


        try {
            DateUtil.checkIfPeriodIsValid(dto.getStartDate(), dto.getEndDate());
        } catch (ApplicationRuntimeException are) {
            model.addAttribute(VIEW_MESSAGE, are.getMessage());
            this.getCreateConference(model);
            return BASE_LAYOUT;
        }

        Conference conference = this.conferenceService.createNewConference(dto, owner);

        model.addAttribute("conference", conference);
        model.addAttribute(VIEW, CONFERENCE_DETAILS);

        return BASE_LAYOUT;
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


    //GET edit view html
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getEditConference(@PathVariable long id, Model model) {
        //TODO check if user own this conference
        model.addAttribute(VIEW, CONFERENCE_EDIT);
        return BASE_LAYOUT;
    }

    //POST edit conference
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editConference(@PathVariable long id, CreateConferenceDto dto, Model model) {
        Conference conference = conferenceService.editConference(id, dto);
        model.addAttribute(VIEW, CONFERENCE_DETAILS);
        model.addAttribute("conference", conference);
        return BASE_LAYOUT;
    }


    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String details(@PathVariable long id, Model model) {
        Conference conference = conferenceService.findConference(id);
        List<Session> sessions = conference.getSessions();
        model.addAttribute("sessions",sessions);
        model.addAttribute(VIEW, CONFERENCE_DETAILS);
        model.addAttribute("conference", conference);
        return BASE_LAYOUT;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable long id, Model model) {
        conferenceService.deleteConferenceById(id);
        return "redirect:/conferences/all";
    }
}
