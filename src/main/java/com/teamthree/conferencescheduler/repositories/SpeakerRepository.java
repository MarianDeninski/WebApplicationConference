package com.teamthree.conferencescheduler.repositories;

import com.teamthree.conferencescheduler.entities.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
    Speaker findByName(String name);
}
