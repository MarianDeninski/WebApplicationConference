package com.teamthree.conferencescheduler.controllers.conference;

import com.teamthree.conferencescheduler.repositories.UserRepository;
import com.teamthree.conferencescheduler.service.api.RoleService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    //Get add view
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddConference(){
        String path = "";
        return path ;
    }

    //Post data to db
    @PostMapping(path = "/add")
    public String addConference(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();


        }
        return "redirect:/login";


    }

}
