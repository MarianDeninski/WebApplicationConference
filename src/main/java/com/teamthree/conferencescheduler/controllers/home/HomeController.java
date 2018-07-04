package com.teamthree.conferencescheduler.controllers.home;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.service.api.HomeService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.HOME_INDEX;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.HOME_BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;

@Controller
public class HomeController {

    private UserService userService;
    private HomeService homeService;

    public  HomeController(){

    }
    @Autowired
    public HomeController(UserService userService, HomeService homeService) {
        this.userService = userService;
        this.homeService=homeService;
    }

    @GetMapping("/")
//    @PreAuthorize("isAuthenticated()")
    public String index(Model model) {

<<<<<<< HEAD
        List<Conference> conferences = new ArrayList<>();

        //conferences.add(new Conference("WORLD PRICE", "Conf descript", "24-XI-2017", "28-XII-2017"));
       // conferences.add(new Conference("HELLO ASSHOLE", "Conf descript", "24-XI-2017", "28-XII-2017"));

        

=======
        List<Conference> conferences = this.homeService.getAllConference();
>>>>>>> bfa8f0c78dc02565b81dd2fa82d000d719251621
        model.addAttribute("conferences",conferences);
        model.addAttribute(VIEW, HOME_INDEX);
        return HOME_BASE_LAYOUT;
    }


}
