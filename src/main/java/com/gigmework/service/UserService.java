package com.gigmework.service;

import com.gigmework.model.User;
import com.gigmework.model.UserRole;
import com.gigmework.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for user-related operations. Provides methods for registering users,
 * fetching users by role and by id.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns all users with the given role.
     * @param role the role to filter by
     * @return list of users
     */
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    /**
     * Returns a user by id.
     * @param id the user id
     * @return optional user
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Registers or updates a user.
     * @param user the user to save
     * @return the saved user
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
