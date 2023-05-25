package edu.modicon.app.domain.repository;

import edu.modicon.app.domain.model.Profile;

import java.util.Optional;

public interface ProfileRepository {

    Optional<Profile> findByUsername(String profileUsername, String currentUsername);

    void followProfile(String username, String currentUsername);

    void unfollowProfile(String username, String currentUsername);
}
