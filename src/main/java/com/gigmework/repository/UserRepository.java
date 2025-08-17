package com.gigmework.repository;

import com.gigmework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

import com.gigmework.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    /**
     * Returns all users with the given role.
     * @param role role to filter by
     * @return list of users
     */
    List<User> findByRole(UserRole role);
}