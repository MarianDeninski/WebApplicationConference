package com.teamthree.conferencescheduler.repositories;

import com.teamthree.conferencescheduler.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
