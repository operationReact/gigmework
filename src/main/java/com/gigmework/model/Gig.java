package com.gigmework.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a gig created by a client. A gig has a budget range and a list
 * of required skills. The status indicates where the gig is in the lifecycle.
 */
@Entity
@Table(name = "gigs")
@Data
public class Gig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(name = "budget_min", nullable = false)
    private Double budgetMin;

    @Column(name = "budget_max", nullable = false)
    private Double budgetMax;

    @ElementCollection
    @CollectionTable(name = "gig_skills", joinColumns = @JoinColumn(name = "gig_id"))
    @Column(name = "skill")
    private List<String> skillsRequired;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GigStatus status;

    private LocalDateTime createdAt;
}