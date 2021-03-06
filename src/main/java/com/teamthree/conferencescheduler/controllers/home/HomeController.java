package com.teamthree.conferencescheduler.controllers.home;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.service.api.HomeService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.HOME_INDEX;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.HOME_INDEX1;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.HOME_INDEX2;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.*;

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


        Map<String, List<Conference>> splitedByDate = this.homeService.splitByPastUpcomingActive(conferences);

        model.addAttribute("pastConferences",splitedByDate.get("past"));
        model.addAttribute("activeConferences",splitedByDate.get("active"));
        model.addAttribute("upcomingConferences",splitedByDate.get("upcoming"));

        model.addAttribute(VIEW, HOME_INDEX);
        model.addAttribute(VIEW1, HOME_INDEX1);
        model.addAttribute(VIEW2, HOME_INDEX2);
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
