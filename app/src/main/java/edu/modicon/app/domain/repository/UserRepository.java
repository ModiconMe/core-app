package edu.modicon.app.domain.repository;

import edu.modicon.app.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Long save(User user);

}
