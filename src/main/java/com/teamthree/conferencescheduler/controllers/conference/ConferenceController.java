package com.teamthree.conferencescheduler.controllers.conference;

import com.teamthree.conferencescheduler.repositories.UserRepository;
import com.teamthree.conferencescheduler.service.api.RoleService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.teamthree.conferencescheduler.constants.views.ViewConstants;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.CREATE_CONFERENCE;

@Controller
@RequestMapping("/conference")
public class ConferenceController {

    private UserService userService;
    private RoleService roleService;
    private UserRepository userRepository;
    public ConferenceController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }
    //Get createConference view
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateConference(Model model){
        model.addAttribute("view",CREATE_CONFERENCE);
        return BASE_LAYOUT ;
    }

    //Post data to db
    @PostMapping(path = "/create")
    public String createConference(){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();


        }
        return "redirect:/login";


    }

}
