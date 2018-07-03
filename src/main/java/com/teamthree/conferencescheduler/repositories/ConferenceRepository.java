package com.teamthree.conferencescheduler.repositories;

import com.teamthree.conferencescheduler.entities.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
}
