package edu.modicon.app.domain.repository;

import edu.modicon.app.domain.model.Profile;

import java.util.Optional;

public interface ProfileRepository {

    Optional<Profile> findByUsername(String profileUsername, String currentUsername);

    Optional<Profile> findById(Long id);

    boolean followProfile(String username, String currentUsername);

    boolean unfollowProfile(String username, String currentUsername);
}
