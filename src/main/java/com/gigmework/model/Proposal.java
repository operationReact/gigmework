package com.gigmework.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a proposal submitted by a freelancer in response to a gig. The
 * proposal includes the bid amount, estimated turnaround time and a cover
 * letter. Proposals can be shortlisted, accepted or rejected.
 */
@Entity
@Table(name = "proposals")
@Data
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gig_id", nullable = false)
    private Gig gig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id", nullable = false)
    private User freelancer;

    @Column(name = "bid_amount", nullable = false)
    private Double bidAmount;

    @Column(name = "eta_days", nullable = false)
    private Integer etaDays;

    @Column(name = "cover_letter", nullable = false, length = 2000)
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status;

    private LocalDateTime createdAt;
}