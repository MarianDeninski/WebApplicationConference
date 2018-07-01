package com.teamthree.conferencescheduler.service.impl;

import com.teamthree.conferencescheduler.entities.Role;
import com.teamthree.conferencescheduler.repositories.RoleRepository;
import com.teamthree.conferencescheduler.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void addNewRole(Role role) {
        this.roleRepository.save(role);
    }
}
