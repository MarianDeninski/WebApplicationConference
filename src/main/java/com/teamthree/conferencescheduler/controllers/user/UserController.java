package com.teamthree.conferencescheduler.controllers.user;

import com.teamthree.conferencescheduler.dto.user.UserRegisterDto;
import com.teamthree.conferencescheduler.entities.Role;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.service.api.RoleService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.teamthree.conferencescheduler.constants.errors.ErrorHandlingConstants.*;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.USER_HOP;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.USER_LOGIN;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.USER_REGISTER;
import static com.teamthree.conferencescheduler.constants.user_roles.UserRoles.ROLE_USER;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW_MESSAGE;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute(VIEW, USER_LOGIN);
        return BASE_LAYOUT;
    }

    @GetMapping("/addspeaker")
    public String hop(Model model) {

        model.addAttribute(VIEW, USER_HOP);
        return BASE_LAYOUT;
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute(VIEW, USER_REGISTER);
        return BASE_LAYOUT;
    }

    @PostMapping("/register")
    public String processRegister(Model model, UserRegisterDto dto) {

        if (this.userService.checkIfUserExists(dto.getUsername())) {
            model.addAttribute(VIEW_MESSAGE, EMAIL_EXISTS);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }

        if (dto.getUsername().isEmpty()) {
            model.addAttribute(VIEW_MESSAGE, EMAIL_FIELD_IS_EMPTY);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }

        if (dto.getFullName().isEmpty()) {
            model.addAttribute(VIEW_MESSAGE, NAME_FIELD_IS_EMPTY);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }

        if (dto.getPassword().isEmpty() || dto.getConfirmPassword().isEmpty()) {
            model.addAttribute(VIEW_MESSAGE, PASSWORD_FIELD_IS_EMPTY);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }


        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute(VIEW_MESSAGE, CONFIRM_PASS_NOT_MATCHING);
            model.addAttribute(VIEW, USER_REGISTER);

            return BASE_LAYOUT;
        }


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(dto.getUsername(),
                dto.getFullName(),
                passwordEncoder.encode(dto.getPassword()));

        Role role = this.roleService.findByName(ROLE_USER);
        user.addRole(role);
        this.userService.createNewUser(user);

        return "redirect:/user/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);

        }
        return "redirect:/user/login?logout";
    }


    @GetMapping("/profile")
//    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userService.findByUsername(principal.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("view", "user/profile");
        return BASE_LAYOUT;
    }
}