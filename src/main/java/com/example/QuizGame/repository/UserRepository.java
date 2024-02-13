package com.example.QuizGame.repository;

import com.example.QuizGame.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA repository for User entities in the Quiz Game application.
 * This interface performing various operations on User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to be found.
     * @return An Optional containing the User object if found,
     * or an empty Optional if no user is found with the given username.
     */
    Optional<User> findByUsername(String username);
}
