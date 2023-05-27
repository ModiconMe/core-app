package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.profile.*;

public interface ProfileService {

    GetProfileResponse getProfile(GetProfileRequest request);

    FollowProfileResponse followProfile(FollowProfileRequest request);

    UnfollowProfileResponse unfollowProfile(UnfollowProfileRequest request);
}
