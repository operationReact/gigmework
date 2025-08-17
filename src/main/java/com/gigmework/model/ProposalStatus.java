package com.gigmework.model;

/**
 * State transitions for proposals. A proposal is initially SENT, then can be
 * SHORTLISTED, ACCEPTED or REJECTED by the client.
 */
public enum ProposalStatus {
    SENT,
    SHORTLISTED,
    REJECTED,
    ACCEPTED
}