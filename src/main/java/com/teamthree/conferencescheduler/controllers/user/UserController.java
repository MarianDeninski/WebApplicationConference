package com.teamthree.conferencescheduler.controllers.user;

import com.teamthree.conferencescheduler.app_utils.DateUtil;
import com.teamthree.conferencescheduler.app_utils.ProgramMaximumUtil;
import com.teamthree.conferencescheduler.dto.program_max.ProgramMaximumDto;
import com.teamthree.conferencescheduler.dto.user.FileUploadDto;
import com.teamthree.conferencescheduler.dto.user.UserRegisterDto;
import com.teamthree.conferencescheduler.dto.venue.AddVenueDto;
import com.teamthree.conferencescheduler.entities.*;
import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;
import com.teamthree.conferencescheduler.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.teamthree.conferencescheduler.constants.errors.ErrorHandlingConstants.*;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.USER_LOGIN;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.USER_REGISTER;
import static com.teamthree.conferencescheduler.constants.user_roles.UserRoles.ROLE_USER;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private ConferenceService conferenceService;
    private VenueService venueService;
    private SessionService sessionService;

    @Autowired
    public UserController(UserService userService, RoleService roleService,
                          ConferenceService conferenceService, VenueService venueService,
                          SessionService sessionService) {

        this.userService = userService;
        this.roleService = roleService;
        this.conferenceService = conferenceService;
        this.venueService = venueService;
        this.sessionService = sessionService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute(VIEW, USER_LOGIN);
        return BASE_LAYOUT;
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute(VIEW, USER_REGISTER);
        return BASE_LAYOUT;
    }

    @PostMapping("/register")
    public String processRegister(Model model, UserRegisterDto dto) {

        if (this.userService.checkIfUserExists(dto.getUsername())) {
            model.addAttribute(VIEW_MESSAGE, EMAIL_EXISTS);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }

        if (dto.getUsername().isEmpty()) {
            model.addAttribute(VIEW_MESSAGE, EMAIL_FIELD_IS_EMPTY);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }

        if (dto.getFullName().isEmpty()) {
            model.addAttribute(VIEW_MESSAGE, NAME_FIELD_IS_EMPTY);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }

        if (dto.getPassword().isEmpty() || dto.getConfirmPassword().isEmpty()) {
            model.addAttribute(VIEW_MESSAGE, PASSWORD_FIELD_IS_EMPTY);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }


        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute(VIEW_MESSAGE, CONFIRM_PASS_NOT_MATCHING);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(dto.getUsername(),
                dto.getFullName(),
                passwordEncoder.encode(dto.getPassword()));

        Role role = this.roleService.findByName(ROLE_USER);
        user.addRole(role);
        this.userService.createNewUser(user);

        return "redirect:/user/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);

        }
        return "redirect:/user/login?logout";
    }


    @GetMapping("/profile")
//    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());

        ArrayList<Conference> conferences = userService.getUserConferences(user);
        List<Venue> venues = user.getVenues();
        if (conferences.isEmpty()) {

        }
        model.addAttribute("conferences", conferences);
        model.addAttribute("venues", venues);
        model.addAttribute("view", "user/profile");
        return "my-profile-base-layout";
    }

    @GetMapping("/conference/{id}")
    public String userConference(Model model, @PathVariable long id) {
        Conference conference = this.conferenceService.findConference(id);
        return "redirect:/conference/details/" + id;
    }


    @GetMapping("/venue/{id}")
    @PreAuthorize("isAuthenticated()")
    public String userEditVenue(Model model, @PathVariable long id) {
        Venue found = this.venueService.getVenueById(id);

        model.addAttribute("venue", found);
        model.addAttribute(VIEW, "venue/show_venue");
        return BASE_LAYOUT;
    }

    @PostMapping("/venue/{id}")
    public String processUserEditVenue(AddVenueDto dto, @PathVariable long id, Model model) {
        Venue venue = this.venueService.getVenueById(id);

        venue.setName(dto.getName());
        venue.setAddress(dto.getAddress());
        try {
            this.venueService.addVenue(venue);
        } catch (ApplicationRuntimeException are) {

            model.addAttribute(VIEW_MESSAGE, are.getMessage());
            model.addAttribute("venue", venue);
            model.addAttribute(VIEW, "venue/show_venue");

            return BASE_LAYOUT;
        }

        return REDIRECT_TO_MY_PROFILE;
    }

    @PostMapping("/image/upload")
    public String imageProcess(FileUploadDto dto) {

        return null;
    }

    @PostMapping("/programme_maximum")
    public String processUserEditVenue(ProgramMaximumDto dto, Model model) {
        Conference conference = this.conferenceService.getByName(dto.getConferenceName());

        List<Session> sessionsByConference =
                this.sessionService.findByConferenceAndDate(conference, dto.getTargetDate());

        List<Session> maximumSessions =
                ProgramMaximumUtil.execute(sessionsByConference, DateUtil.getCurrentTimeAsString());

        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());
        for (Session maximumSession : maximumSessions) {
            this.sessionService.addUserToSession(user, maximumSession.getId());
        }
        model.addAttribute(VIEW,SHOW_TO_USER_PROGRAMME);
        model.addAttribute("sessions",maximumSessions);

        return BASE_LAYOUT;
    }

    @RequestMapping(value = "/programme_maximum", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String programeMaximum(Model model) {
        // TODO: User should not be able to choose from confs that are past ?
        List<Conference> allConferences = this.conferenceService.getAllConferences();


        model.addAttribute("allConferences", allConferences);
        model.addAttribute(VIEW, "programme_maximum/execute");
        return BASE_LAYOUT;

    }

//    @RequestMapping(value = "/programme_maximum/{id}", method = RequestMethod.POST)
//    @PreAuthorize("isAuthenticated()")
//    public String processProgrameMaximum(Model model, Principal principal,
//                                         @PathVariable long id, ProgramMaximumDto dto) {
//
//        Conference conference = this.conferenceService.getById(id);
//        List<Session> sessionsByConference =
//                this.sessionService.findByConference(conference);
//
//        List<Session> maximumSessions =
//                ProgramMaximumUtil.execute(sessionsByConference, DateUtil.getCurrentTimeAsString());
//
//        User user = this.userService.findByUsername(principal.getName());
//        for (Session maximumSession : maximumSessions) {
//            this.sessionService.addUserToSession(user, maximumSession.getId());
//        }
//
//
//        return REDIRECT_TO_MY_PROFILE;
//    }

    @RequestMapping(value = "/programme_maximum/info", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String processInfoMaximum(Model model) {

        List<Conference> allConferences = this.conferenceService.getAllConferences();


        model.addAttribute("allConferences", allConferences);
        model.addAttribute(VIEW, "programme_maximum/execute");
        return BASE_LAYOUT;

    }

    @RequestMapping(value ="/joinsession/{id}",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String addUserToSession(@PathVariable Long sessionId){
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());
       // Session session = this.sessionService.getById(sessionId);
        this.sessionService.addUserToSession(user,sessionId);
        return "redirect:/session/details/"+sessionId;
    }

}