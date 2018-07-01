package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service("conferenceUserDetailsServiceImpl")
public class ConferenceUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public ConferenceUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid User");
        } else {
            Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());

            return new org.
                    springframework.
                    security.
                    core.
                    userdetails.
                    User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }
    }
}
