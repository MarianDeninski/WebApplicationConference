package com.teamthree.conferencescheduler.controllers.session;

import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.entities.Session;
import com.teamthree.conferencescheduler.repositories.SessionRepository;
import com.teamthree.conferencescheduler.service.api.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

@Controller
@RequestMapping("/session")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService=sessionService;
    }

    @RequestMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String getCreateSession(){
        return null;
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String createSession(SessionDto dto, Model model){
        Session session =this.sessionService.createSession(dto);
        model.addAttribute("session", session);
        model.addAttribute(VIEW, SESSION_DETAILS);

        return BASE_LAYOUT;
    }
    //TODO get all sessions
    //TODO CRUD operations on sessions

}
