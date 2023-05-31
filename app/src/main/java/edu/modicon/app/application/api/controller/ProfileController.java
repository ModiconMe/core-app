package edu.modicon.app.application.api.controller;

import edu.modicon.app.application.api.BaseController;
import edu.modicon.app.application.api.ProfileEndpoint;
import edu.modicon.app.application.dto.profile.*;
import edu.modicon.app.domain.constant.Constant;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constant.Endpoint.PROFILES)
public class ProfileController extends BaseController implements ProfileEndpoint {

    @Override
    public GetProfileResponse getProfile(String username, AppUserDetails currentUser) {
        return bus.executeQuery(new GetProfileRequest(username, currentUser.getUsername()));
    }

    @Override
    public FollowProfileResponse followUser(String username) {
        return bus.executeCommand(new FollowProfileRequest(username, getCurrentUser().getUsername()));
    }

    @Override
    public UnfollowProfileResponse unfollowUser(String username) {
        return bus.executeCommand(new UnfollowProfileRequest(username, getCurrentUser().getUsername()));
    }
}
