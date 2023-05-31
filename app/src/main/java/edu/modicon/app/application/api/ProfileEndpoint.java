package edu.modicon.app.application.api;

import edu.modicon.app.application.dto.profile.*;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface ProfileEndpoint {

    @GetMapping("{username}")
    GetProfileResponse getProfile(@PathVariable String username, @AuthenticationPrincipal AppUserDetails currentUser);

    @PostMapping("{username}/follow")
    FollowProfileResponse followUser(@PathVariable String username);

    @DeleteMapping("{username}/follow")
    UnfollowProfileResponse unfollowUser(@PathVariable String username);
}
