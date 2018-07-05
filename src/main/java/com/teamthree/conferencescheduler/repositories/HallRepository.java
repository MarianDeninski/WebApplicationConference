package com.teamthree.conferencescheduler.repositories;

import com.teamthree.conferencescheduler.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall,Long> {
    Hall findById(long id);

    List<Hall> findByVenueIsLike(String venueName);

    Hall findByName(String name);
}
