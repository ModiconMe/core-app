package edu.modicon.app.application.api;

import edu.modicon.app.application.dto.profile.FollowProfileRequest;
import edu.modicon.app.application.dto.profile.FollowProfileResponse;
import edu.modicon.app.application.dto.profile.GetProfileRequest;
import edu.modicon.app.application.dto.profile.GetProfileResponse;
import edu.modicon.app.domain.constant.Constant;
import edu.modicon.app.domain.service.ProfileService;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constant.Endpoint.PROFILES)
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("{username}")
    public GetProfileResponse getProfile(@PathVariable String username, @AuthenticationPrincipal AppUserDetails currentUser) {
        return profileService.getProfile(new GetProfileRequest(username, currentUser.getUsername()));
    }

    @PostMapping("{username}/follow")
    public FollowProfileResponse followUser(@PathVariable String username, @AuthenticationPrincipal AppUserDetails currentUser) {
        return profileService.followProfile(new FollowProfileRequest(username, currentUser.getUsername()));
    }
}
