package com.gigmework.model;

/**
 * State transitions for gigs. A new gig starts as OPEN, a freelancer is hired
 * then the gig becomes IN_PROGRESS, and finally COMPLETED or CANCELLED.
 */
public enum GigStatus {
    OPEN,
    HIRED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}