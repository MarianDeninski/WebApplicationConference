package com.teamthree.conferencescheduler.controllers.user;

import com.teamthree.conferencescheduler.dto.conference.CreateConferenceDto;
import com.teamthree.conferencescheduler.dto.user.UserRegisterDto;
import com.teamthree.conferencescheduler.dto.venue.AddVenueDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Role;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.entities.Venue;
import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;
import com.teamthree.conferencescheduler.service.api.ConferenceService;
import com.teamthree.conferencescheduler.service.api.RoleService;
import com.teamthree.conferencescheduler.service.api.UserService;
import com.teamthree.conferencescheduler.service.api.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserController(UserService userService, RoleService roleService,
                          ConferenceService conferenceService, VenueService venueService) {

        this.userService = userService;
        this.roleService = roleService;
        this.conferenceService = conferenceService;
        this.venueService = venueService;
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

        // TODO: FOR TESTING PURPOSES ONLY REMOVE WHEN THE LOGIC IS IMPLEMENTED FOR CONFERENCES


        List<Conference> conferences = new ArrayList<>();
        conferences.add(new Conference("Conf name", "Conf descript", "24-XI-2017", "28-XII-2017"));
        conferences.add(new Conference("Conf name", "Conf descript", "24-XI-2017", "28-XII-2017"));
        conferences.add(new Conference("Conf name", "Conf descript", "24-XI-2017", "28-XII-2017"));
        conferences.add(new Conference("Conf name", "Conf descript", "24-XI-2017", "28-XII-2017"));
        user.setConferencesList(conferences);
        // TODO: FOR TESTING PURPOSES ONLY REMOVE WHEN THE LOGIC IS IMPLEMENTED FOR CONFERENCES

        model.addAttribute("conferences", conferences);
        model.addAttribute("venues", user.getVenues());
        model.addAttribute("view", "user/profile");
        return "my-profile-base-layout";
    }

    @GetMapping("/conefrence/{id}")
    public String userConference(Model model, @PathVariable long id) {
        Conference conference = this.conferenceService.findConference(id);

        model.addAttribute("conference", conference);
        model.addAttribute(VIEW, USER_LOGIN);
        return BASE_LAYOUT;
    }

    @PostMapping("/conefrence/{id}")
    //TODO: MAKE VIEW FOR THIS METHOD AND ADD MORE ATTRIBUTES TO THE DTO
    public String processUserEditConference(CreateConferenceDto dto, @PathVariable long id) {
        Conference conference = this.conferenceService.findConference(id);

        conference.setName(dto.getName());
        conference.setDescription(dto.getDescription());
//          TODO: CREATE METHOD TO SAVE TO THE DB NEW CONFEREENCE
//        this.conferenceService

        return REDIRECT_TO_MY_PROFILE;
    }

    @GetMapping("/venue/{id}")
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

            //FIXME: REMOVE MAGICAL TEXT AND NUMBERS
            model.addAttribute(VIEW_MESSAGE, "Please make some changes, or click the cancel button.");
            model.addAttribute("venue", venue);
            model.addAttribute(VIEW, "venue/show_venue");

            return BASE_LAYOUT;
        }

        return REDIRECT_TO_MY_PROFILE;
    }
}
