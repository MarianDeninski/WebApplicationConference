package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.entities.Role;

public interface RoleService {
    Role findByName(String roleName);
}
