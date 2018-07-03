package com.teamthree.conferencescheduler.app_utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public Authentication getLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
