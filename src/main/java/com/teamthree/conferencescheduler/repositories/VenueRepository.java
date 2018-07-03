package com.teamthree.conferencescheduler.repositories;

import com.teamthree.conferencescheduler.entities.Conference;
import com.teamthree.conferencescheduler.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue,Long> {
    Venue findByName(String name);
}
