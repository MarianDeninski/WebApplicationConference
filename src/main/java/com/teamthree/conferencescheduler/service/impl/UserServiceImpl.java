package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.repositories.UserRepository;
import com.teamthree.conferencescheduler.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createNewUser(User user) {
        this.userRepository.saveAndFlush(user);
    }
}
