package com.teamthree.conferencescheduler.controllers.error;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class  ApplicationErrorController implements ErrorController {

    @Override
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String getErrorPath() {
        return "error-layout";
    }
}
