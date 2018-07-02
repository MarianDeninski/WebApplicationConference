package com.teamthree.conferencescheduler.controllers.user;

import com.teamthree.conferencescheduler.dto.user.UserRegisterDto;
import com.teamthree.conferencescheduler.entities.Role;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.service.api.RoleService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.USER_LOGIN;
import static com.teamthree.conferencescheduler.constants.roadsMappings.RoadMapping.USER_REGISTER;
import static com.teamthree.conferencescheduler.constants.user_roles.UserRoles.ROLE_USER;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.BASE_LAYOUT;
import static com.teamthree.conferencescheduler.constants.views.ViewConstants.VIEW;

@Controller
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


    @GetMapping("/register")
    public String register(Model model) {


        model.addAttribute(VIEW, USER_REGISTER);
        return BASE_LAYOUT;
    }

    @PostMapping("/register")
    public String processRegister(UserRegisterDto dto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(dto.getUsername(),
                dto.getFullName(),
                passwordEncoder.encode(dto.getPassword()));

        Role role = this.roleService.findByName(ROLE_USER);
        user.addRole(role);
        this.userService.createNewUser(user);

        return "redirect:/login";
    }

    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
