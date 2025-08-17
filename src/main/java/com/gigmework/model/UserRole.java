package com.gigmework.model;

/**
 * Enumerates the possible roles a user can have. Clients create gigs while
 * freelancers submit proposals and complete the work. Admins can manage
 * disputes, payouts and moderate the platform.
 */
public enum UserRole {
    CLIENT,
    FREELANCER,
    ADMIN
}