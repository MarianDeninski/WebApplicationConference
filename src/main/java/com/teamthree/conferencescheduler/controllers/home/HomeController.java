package com.teamthree.conferencescheduler.controllers.home;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.service.api.HomeService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.HOME_INDEX;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.HOME_BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;

@Controller
public class HomeController {

    private UserService userService;
    private HomeService homeService;

    public HomeController() {

    }

    @Autowired
    public HomeController(UserService userService, HomeService homeService) {
        this.userService = userService;
        this.homeService = homeService;
    }

    @GetMapping("/")
//    @PreAuthorize("isAuthenticated()")
    public String index(Model model) {
        List<Conference> conferences = this.homeService.getAllConference();

        conferences.forEach(conference -> {
            if (conference.getName().length() > 12) {
                conference.setName(conference.getName().substring(0, 10) + "...");
            } else {
                conference.setName(conference.getName());
            }
        });


        model.addAttribute("conferences", conferences);
        model.addAttribute(VIEW, HOME_INDEX);
        return HOME_BASE_LAYOUT;
    }
//    @GetMapping("/details")
//
//    public String details(Model model){
//
//
//
//
//    }


}
