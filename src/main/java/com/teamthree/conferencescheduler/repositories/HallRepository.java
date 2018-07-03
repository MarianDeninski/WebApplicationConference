package com.teamthree.conferencescheduler.repositories;

import com.teamthree.conferencescheduler.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall,Long> {
}
