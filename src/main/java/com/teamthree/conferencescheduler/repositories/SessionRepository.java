package com.teamthree.conferencescheduler.repositories;

import com.teamthree.conferencescheduler.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
