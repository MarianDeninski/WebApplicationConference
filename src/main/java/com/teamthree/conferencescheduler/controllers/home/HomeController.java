package com.teamthree.conferencescheduler.controllers.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.HOME_INDEX;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/Index")
    public String index(Model model) {
        model.addAttribute(VIEW, HOME_INDEX);
        return BASE_LAYOUT;
    }


}
