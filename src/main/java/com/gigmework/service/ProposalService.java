package com.gigmework.service;

import com.gigmework.model.Gig;
import com.gigmework.model.Proposal;
import com.gigmework.model.ProposalStatus;
import com.gigmework.model.User;
import com.gigmework.repository.GigRepository;
import com.gigmework.repository.ProposalRepository;
import com.gigmework.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing proposals.
 */
@Service
public class ProposalService {
    private final ProposalRepository proposalRepository;
    private final GigRepository gigRepository;
    private final UserRepository userRepository;

    public ProposalService(ProposalRepository proposalRepository,
                           GigRepository gigRepository,
                           UserRepository userRepository) {
        this.proposalRepository = proposalRepository;
        this.gigRepository = gigRepository;
        this.userRepository = userRepository;
    }

    /**
     * Returns all proposals for a given gig.
     * @param gigId the gig id
     * @return list of proposals
     */
    public List<Proposal> getProposalsByGig(Long gigId) {
        Gig gig = gigRepository.findById(gigId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid gig id"));
        return proposalRepository.findByGig(gig);
    }

    /**
     * Submits a new proposal for a gig by a freelancer.
     * @param gigId the gig id
     * @param freelancerId the freelancer's id
     * @param proposal the proposal details
     * @return the saved proposal
     */
    public Proposal submitProposal(Long gigId, Long freelancerId, Proposal proposal) {
        Gig gig = gigRepository.findById(gigId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid gig id"));
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid freelancer id"));
        proposal.setGig(gig);
        proposal.setFreelancer(freelancer);
        proposal.setStatus(ProposalStatus.SENT);
        proposal.setCreatedAt(LocalDateTime.now());
        return proposalRepository.save(proposal);
    }

    /**
     * Returns a proposal by id.
     * @param id the proposal id
     * @return optional proposal
     */
    public Optional<Proposal> getProposalById(Long id) {
        return proposalRepository.findById(id);
    }

    /**
     * Updates an existing proposal.
     * @param id the id of the proposal to update
     * @param updates updated proposal details
     * @return optional updated proposal
     */
    public Optional<Proposal> updateProposal(Long id, Proposal updates) {
        return proposalRepository.findById(id).map(existing -> {
            existing.setBidAmount(updates.getBidAmount());
            existing.setEtaDays(updates.getEtaDays());
            existing.setCoverLetter(updates.getCoverLetter());
            existing.setStatus(updates.getStatus());
            return proposalRepository.save(existing);
        });
    }
}
