package com.gigmework.service;

import com.gigmework.model.Gig;
import com.gigmework.model.User;
import com.gigmework.repository.GigRepository;
import com.gigmework.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing gigs. Handles CRUD operations on gigs.
 */
@Service
public class GigService {
    private final GigRepository gigRepository;
    private final UserRepository userRepository;

    public GigService(GigRepository gigRepository, UserRepository userRepository) {
        this.gigRepository = gigRepository;
        this.userRepository = userRepository;
    }

    /**
     * Returns all gigs in the system.
     * @return list of gigs
     */
    public List<Gig> getAllGigs() {
        return gigRepository.findAll();
    }

    /**
     * Returns a gig by id.
     * @param id the gig id
     * @return optional gig
     */
    public Optional<Gig> getGigById(Long id) {
        return gigRepository.findById(id);
    }

    /**
     * Creates a new gig for the given client.
     * @param clientId the id of the client creating the gig
     * @param gig the gig details
     * @return the saved gig
     */
    public Gig createGig(Long clientId, Gig gig) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client id"));
        gig.setClient(client);
        return gigRepository.save(gig);
    }

    /**
     * Updates an existing gig.
     * @param id the id of the gig to update
     * @param updates the updated gig details
     * @return optional updated gig
     */
    public Optional<Gig> updateGig(Long id, Gig updates) {
        return gigRepository.findById(id).map(existing -> {
            existing.setTitle(updates.getTitle());
            existing.setDescription(updates.getDescription());
            existing.setBudgetMin(updates.getBudgetMin());
            existing.setBudgetMax(updates.getBudgetMax());
            existing.setSkillsRequired(updates.getSkillsRequired());
            existing.setStatus(updates.getStatus());
            return gigRepository.save(existing);
        });
    }

    /**
     * Deletes a gig by id.
     * @param id the gig id
     */
    public void deleteGig(Long id) {
        gigRepository.deleteById(id);
    }
}
