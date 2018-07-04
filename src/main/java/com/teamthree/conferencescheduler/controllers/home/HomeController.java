package com.teamthree.conferencescheduler.controllers.home;

import com.teamthree.conferencescheduler.entities.Conference;
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

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
//    @PreAuthorize("isAuthenticated()")
    public String index(Model model) {

        List<Conference> conferences = new ArrayList<>();
        conferences.add(new Conference("Marketing", "Conf descript", "24-XI-2017", "28-XII-2017"));
        conferences.add(new Conference("Dev hacks", "Conf descript", "24-XI-2017", "28-XII-2017"));
        conferences.add(new Conference("QA", "Conf descript", "24-XI-2017", "28-XII-2017"));
        conferences.add(new Conference("WORLD PRICE", "Conf descript", "24-XI-2017", "28-XII-2017"));


        model.addAttribute("conferences",conferences);


        model.addAttribute(VIEW, HOME_INDEX);
        return HOME_BASE_LAYOUT;
    }




}
