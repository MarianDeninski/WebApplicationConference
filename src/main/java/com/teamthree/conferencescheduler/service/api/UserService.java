package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.User;

public interface UserService {
    void createNewUser(User user);

    User findByUsername(String username);
}
