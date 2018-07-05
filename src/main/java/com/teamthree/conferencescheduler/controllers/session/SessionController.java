package com.teamthree.conferencescheduler.controllers.session;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.repositories.SessionRepository;
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

    private SessionService sessionService;
    private UserService userService;

    @Autowired
    public SessionController(SessionService sessionService, UserService userService){

        this.sessionService=sessionService;
        this.userService =userService;

    }

    @RequestMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String getCreateSession(Model model){
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());
        List<Conference> userConferences = this.sessionService.getAllConferencesOwnByUser(user);

        //TODO this have to be in thyme leaf if else statement , if he don't own conferences he cant create session
        if(userConferences.isEmpty()){
            //UserConferences should never be empty after we add constraints;
        }
        model.addAttribute("conferences",userConferences);
        model.addAttribute(VIEW,CREATE_SESSION);
        return BASE_LAYOUT;

    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createSession(SessionDto dto, Model model){
        Session session =this.sessionService.createSession(dto);
        model.addAttribute("session", session);
        model.addAttribute(VIEW, SESSION_DETAILS);
        return BASE_LAYOUT;
    }

    @RequestMapping(value = "/edit/id",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String getEditPage(@PathVariable Long id,Model model){
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());

        boolean checkIfUserIsOwnerOfConference= this.sessionService.checkIfUserIsOwnerOfConference(id, user);
        if(!checkIfUserIsOwnerOfConference){
            return "redirect:/home/index";
        }
        model.addAttribute(VIEW , SESSION_EDIT);
        return BASE_LAYOUT;
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editPage(@PathVariable Long id, SessionDto dto, Model model){

        Session session=this.sessionService.editSession(id,dto);

        model.addAttribute("session",session);
        model.addAttribute(VIEW,SESSION_DETAILS);
        return BASE_LAYOUT ;
    }

    @RequestMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getDetails(@PathVariable Long id, Model model){
        Session session = this.sessionService.getById(id);
        if(session==null){
            return "redirect:/home/index";
        }
        model.addAttribute("session", session);
        model.addAttribute(VIEW,SESSION_DETAILS);
        return BASE_LAYOUT;
    }

    @PostMapping("/delete/{id}")
    public String deleteSession(@PathVariable Long id){
        this.sessionService.deleteById(id);
        return "redirect:/home/index";
    }


}
