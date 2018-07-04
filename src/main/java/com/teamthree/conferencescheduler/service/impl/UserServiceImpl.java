package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.repositories.UserRepository;
import com.teamthree.conferencescheduler.service.api.ConferenceService;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ConferenceService conferenceService;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConferenceService conferenceService) {
        this.userRepository = userRepository;
        this.conferenceService = conferenceService;
    }

    @Override
    public void createNewUser(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public boolean checkIfUserExists(String username) {
        return this.findByUsername(username) != null;
    }

    @Override
    public ArrayList<Conference> getUserConferences(User user) {

        ArrayList<Conference> allConferences = (ArrayList<Conference>) conferenceService.getAllConferences();
        //implemented the stupid way cuz i sux at streams

        ArrayList<Conference> conferences = new ArrayList<Conference>();
        for (Conference conference : allConferences) {
            if(conference.getOwner().getId() == user.getId()){
                conferences.add(conference);
            }
        }
        return conferences;
    }


}
