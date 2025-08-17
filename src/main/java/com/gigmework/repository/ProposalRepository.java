package com.gigmework.repository;

import com.gigmework.model.Gig;
import com.gigmework.model.Proposal;
import com.gigmework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByGig(Gig gig);
    List<Proposal> findByFreelancer(User freelancer);
}