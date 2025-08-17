package com.gigmework.repository;

import com.gigmework.model.Gig;
import com.gigmework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GigRepository extends JpaRepository<Gig, Long> {
    List<Gig> findByClient(User client);
}