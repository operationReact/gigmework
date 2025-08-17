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
}