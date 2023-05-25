package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.profile.GetProfileRequest;
import edu.modicon.app.application.dto.profile.GetProfileResponse;

public interface ProfileService {

    GetProfileResponse getProfile(GetProfileRequest request);

}
