package com.gigmework.controller;

import com.gigmework.model.Gig;
import com.gigmework.model.GigStatus;
import com.gigmework.model.User;
import com.gigmework.repository.GigRepository;
import com.gigmework.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller handling gig CRUD operations. For simplicity this example
 * implements only listing and creation. Authentication and authorization
 * should be added to restrict access to these endpoints.
 */
@RestController
@RequestMapping("/api/gigs")
public class GigController {

    private final GigRepository gigRepository;
    private final UserRepository userRepository;

    public GigController(GigRepository gigRepository, UserRepository userRepository) {
        this.gigRepository = gigRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Gig> listGigs() {
        return gigRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Gig> createGig(@RequestParam Long clientId, @Valid @RequestBody Gig gig) {
        Optional<User> clientOpt = userRepository.findById(clientId);
        if (clientOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        gig.setClient(clientOpt.get());
        gig.setStatus(GigStatus.OPEN);
        gig.setCreatedAt(LocalDateTime.now());
        gigRepository.save(gig);
        return ResponseEntity.ok(gig);
    }

    /**
     * Returns a single gig by its id.
     *
     * @param id the gig identifier
     * @return the gig or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gig> getGig(@PathVariable Long id) {
        return gigRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing gig.
     *
     * @param id the id of the gig to update
     * @param updatedGig the updated gig details
     * @return the updated gig or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gig> updateGig(@PathVariable Long id, @Valid @RequestBody Gig updatedGig) {
        return gigRepository.findById(id).map(existing -> {
            existing.setTitle(updatedGig.getTitle());
            existing.setDescription(updatedGig.getDescription());
            existing.setBudgetMin(updatedGig.getBudgetMin());
            existing.setBudgetMax(updatedGig.getBudgetMax());
            existing.setSkillsRequired(updatedGig.getSkillsRequired());
            existing.setStatus(updatedGig.getStatus());
            Gig saved = gigRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a gig by id.
     *
     * @param id the gig id
     * @return 204 No Content or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGig(@PathVariable Long id) {
        if (!gigRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gigRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}