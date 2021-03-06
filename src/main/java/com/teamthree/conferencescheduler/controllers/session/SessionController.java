package com.teamthree.conferencescheduler.controllers.session;

import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.dto.session.SessionDto2;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;
import com.teamthree.conferencescheduler.service.api.ConferenceService;
import com.teamthree.conferencescheduler.service.api.SessionService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/session")
public class SessionController {

    // I use seminar for variable name cause session is reserved name for model.addAttribute()


    // private static Session staticSession;
    private static SessionDto2 addSessionToHallDto;
    private SessionService sessionService;
    private UserService userService;
    private ConferenceService conferenceService;
    //couldn't think of a smarter way atm... :/
    // private static long sessionId;

    @Autowired
    public SessionController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;

    }

    @RequestMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String getCreateSession(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();


        User user = this.userService.findByUsername(principal.getUsername());
        List<Conference> userConferences = this.sessionService.getAllConferencesOwnByUser(user);
        if (userConferences.isEmpty()) {
            //UserConferences should never be empty after we add constraints;
        }

        model.addAttribute("conferences", userConferences);
        model.addAttribute(VIEW, CREATE_SESSION);
        return BASE_LAYOUT;

    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createSession(SessionDto dto, Model model) {

        try {
            addSessionToHallDto = this.sessionService.createSession(dto);
            addSessionToHallDto = this.sessionService.createSession(dto);
        } catch (ApplicationRuntimeException are) {
            UserDetails principal = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();


            User user = this.userService.findByUsername(principal.getUsername());
            List<Conference> userConferences = this.sessionService.getAllConferencesOwnByUser(user);
            model.addAttribute("conference", userConferences);
            model.addAttribute(VIEW_MESSAGE, are.getMessage());
            model.addAttribute(VIEW, CREATE_SESSION);
            return BASE_LAYOUT;
        }

        Conference conference = addSessionToHallDto.getConference();
        model.addAttribute("conference", conference);
        model.addAttribute(VIEW, SESSION_ADD_HALL);
        return BASE_LAYOUT;
    }

    @PostMapping("/addhall")
    @PreAuthorize("isAuthenticated()")
    public String addHall(SessionDto dto, Model model) {

        //Session seminar=this.sessionService.addSessionToHall(dto,sessionWorkingOn);
        try {
            Conference conference = addSessionToHallDto.getConference();
            Session seminar = this.sessionService.addSessionToHall(dto, conference);
            model.addAttribute("seminar", seminar);
            model.addAttribute(VIEW, SESSION_DETAILS);
            return BASE_LAYOUT;
        } catch (ApplicationRuntimeException ex) {
            Conference conference = addSessionToHallDto.getConference();
            model.addAttribute("conference", conference);
            model.addAttribute(VIEW_MESSAGE, ex.getMessage());
            model.addAttribute(VIEW, SESSION_ADD_HALL);
            return BASE_LAYOUT;
        }


    }

//    @RequestMapping("addhall")
//    @PreAuthorize("isAuthenticated()")
//    public String getAddHall(Model model){
//        Conference conference = this.sessionService.getConferenceById(this.conferenceId);
//        model.addAttribute("conference",conference);
//        model.addAttribute(VIEW,SESSION_ADD_HALL);
//        return BASE_LAYOUT;
//    }

    @RequestMapping(value = "/edit/id", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String getEditPage(@PathVariable Long id, Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());

        boolean checkIfUserIsOwnerOfConference = this.sessionService.checkIfUserIsOwnerOfConference(id, user);
        if (!checkIfUserIsOwnerOfConference) {
            return "redirect:/";
        }
        Session seminar = this.sessionService.getById(id);
        model.addAttribute("seminar", seminar);
        model.addAttribute(VIEW, SESSION_EDIT);
        return BASE_LAYOUT;
    }

    @RequestMapping(value = "/joinsession", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String assertAttendance(@PathVariable Long sessionId, Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());
        this.sessionService.addUserToSession(user, sessionId);
        Conference conference = this.sessionService.getConferenceBySessionId(sessionId);

        return "redirect:/conference/details/" + conference.getId();
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editPage(@PathVariable Long id, SessionDto dto, Model model) {

        Session seminar = this.sessionService.editSession(id, dto);

        model.addAttribute("seminar", seminar);
        model.addAttribute(VIEW, SESSION_DETAILS);
        return BASE_LAYOUT;
    }

    @RequestMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getDetails(@PathVariable Long id, Model model) {
        Session seminar = this.sessionService.getById(id);
        if (seminar == null) {
            return "redirect:/";
        }

        boolean ownerButton = false;

        if (SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal().equals("anonymousUser")) {
            ownerButton = false;
        } else {
            UserDetails principal = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            User user = this.userService.findByUsername(principal.getUsername());

            if (user.getId() == seminar.getConference().getOwner().getId()) {
                ownerButton = true;
            }
        }


        model.addAttribute("ownerButton", ownerButton);
        model.addAttribute("seminar", seminar);
        model.addAttribute(VIEW, SESSION_DETAILS);
        return BASE_LAYOUT;
    }

    @PostMapping("/delete/{id}")
    public String deleteSession(@PathVariable Long id) {
        this.sessionService.deleteById(id);
        return "redirect:/home/index";
    }


}
