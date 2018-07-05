package com.teamthree.conferencescheduler.repositories;

import com.teamthree.conferencescheduler.entities.User;
import com.teamthree.conferencescheduler.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    Venue findById(long id);

    Venue findByAddress(String adress);

    Venue findByName(String name);

    List<Venue> findByOwner(User user);

}
