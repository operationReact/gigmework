package com.gigmework.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a user in the gigmework system. Users can be either clients or
 * freelancers. Additional attributes like rating and KYC status are stored
 * for future use.
 */
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private Double ratingAvg;

    private Integer ratingCount;

    private String kycStatus;

    private LocalDateTime createdAt;

    // For simplicity Lombok will generate getters, setters and toString.
}