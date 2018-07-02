package com.teamthree.conferencescheduler.controllers.home;

import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.HOME_INDEX;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;

@Controller
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
//    @PreAuthorize("isAuthenticated()")
    public String index(Model model) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());

        model.addAttribute("user", user);
        model.addAttribute(VIEW, HOME_INDEX);
        return BASE_LAYOUT;
    }


}
