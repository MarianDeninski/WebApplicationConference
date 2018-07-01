package com.teamthree.conferencescheduler.controllers;

import com.teamthree.conferencescheduler.dto.user.UserRegisterDto;
import com.teamthree.conferencescheduler.entities.Role;
import com.teamthree.conferencescheduler.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.USER_REGISTER;
import static com.teamthree.conferencescheduler.constants.user_roles.UserRoles.ROLE_USER;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;

@Controller
public class UserController {


    @GetMapping("/register")
    public String register(Model model) {


        model.addAttribute(VIEW, USER_REGISTER);
        return BASE_LAYOUT;
    }

    @PostMapping("/register")
    public String processRegister(Model model, UserRegisterDto dto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(dto.getUsername(),
                dto.getFullName(),
                passwordEncoder.encode(dto.getPassword()));

        Role role = new Role(ROLE_USER);


        model.addAttribute(VIEW, USER_REGISTER);
        return BASE_LAYOUT;
    }
}
