package com.teamthree.conferencescheduler.controllers.user;

import com.teamthree.conferencescheduler.dto.ProfileImageDto;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class FileUpload {

    UserService userService;

    @Autowired
    public FileUpload(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/upload", method = RequestMethod.POST)
    public String handleFileUpload(@Valid ProfileImageDto profileImageDto,
                                   BindingResult result,
                                   ModelMap model,
                                   Principal principal) {
        String profileRedirectUrl = String.format("redirect:/%s?ico", )

        return null;
    }
}
