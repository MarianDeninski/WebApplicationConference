package com.teamthree.conferencescheduler.controllers;

import com.teamthree.conferencescheduler.repositories.RoleRepository;
import com.teamthree.conferencescheduler.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");

        return "base-layout";
    }
}
