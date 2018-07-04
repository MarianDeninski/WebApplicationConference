package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.User;

import java.util.ArrayList;

public interface UserService {
    void createNewUser(User user);

    User findByUsername(String username);

    boolean checkIfUserExists(String username);

    ArrayList<Conference> getUserConferences(User user);
}
