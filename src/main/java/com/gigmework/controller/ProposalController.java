package com.gigmework.controller;

import com.gigmework.model.Gig;
import com.gigmework.model.Proposal;
import com.gigmework.model.ProposalStatus;
import com.gigmework.model.User;
import com.gigmework.repository.GigRepository;
import com.gigmework.repository.ProposalRepository;
import com.gigmework.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller for submitting proposals and listing proposals by gig. This
 * controller does not implement authentication; it assumes that the
 * caller provides valid IDs.
 */
@RestController
@RequestMapping("/api/gigs/{gigId}/proposals")
public class ProposalController {
    private final ProposalRepository proposalRepository;
    private final GigRepository gigRepository;
    private final UserRepository userRepository;

    public ProposalController(ProposalRepository proposalRepository,
                              GigRepository gigRepository,
                              UserRepository userRepository) {
        this.proposalRepository = proposalRepository;
        this.gigRepository = gigRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Proposal>> listProposals(@PathVariable Long gigId) {
        Optional<Gig> gigOpt = gigRepository.findById(gigId);
        return gigOpt.map(gig -> ResponseEntity.ok(proposalRepository.findByGig(gig)))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proposal> submitProposal(@PathVariable Long gigId,
                                                   @RequestParam Long freelancerId,
                                                   @Valid @RequestBody Proposal proposal) {
        Optional<Gig> gigOpt = gigRepository.findById(gigId);
        Optional<User> freelancerOpt = userRepository.findById(freelancerId);
        if (gigOpt.isEmpty() || freelancerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        proposal.setGig(gigOpt.get());
        proposal.setFreelancer(freelancerOpt.get());
        proposal.setStatus(ProposalStatus.SENT);
        proposal.setCreatedAt(LocalDateTime.now());
        proposalRepository.save(proposal);
        return ResponseEntity.ok(proposal);
    }

    /**
     * Returns a proposal by id.
     *
     * @param gigId the gig id
     * @param proposalId the proposal id
     * @return the proposal or 404 if not found
     */
    @GetMapping("/{proposalId}")
    public ResponseEntity<Proposal> getProposal(@PathVariable Long gigId,
                                                @PathVariable Long proposalId) {
        return proposalRepository.findById(proposalId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing proposal.
     *
     * @param gigId the gig id
     * @param proposalId the id of the proposal to update
     * @param updated updated proposal details
     * @return the updated proposal or 404 if not found
     */
    @PutMapping("/{proposalId}")
    public ResponseEntity<Proposal> updateProposal(@PathVariable Long gigId,
                                                   @PathVariable Long proposalId,
                                                   @Valid @RequestBody Proposal updated) {
        return proposalRepository.findById(proposalId).map(existing -> {
            existing.setBidAmount(updated.getBidAmount());
            existing.setEtaDays(updated.getEtaDays());
            existing.setCoverLetter(updated.getCoverLetter());
            existing.setStatus(updated.getStatus());
            Proposal saved = proposalRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}