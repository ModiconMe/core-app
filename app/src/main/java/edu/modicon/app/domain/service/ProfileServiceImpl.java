package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.profile.*;
import edu.modicon.app.domain.mapper.ProfileMapper;
import edu.modicon.app.domain.model.Profile;
import edu.modicon.app.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static edu.modicon.app.application.dto.ApiException.notFound;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public GetProfileResponse getProfile(GetProfileRequest request) {
        return new GetProfileResponse(
                profileMapper.apply(
                        getValidProfile(request.getUsername(), request.getCurrentUsername())
                )
        );
    }

    private Profile getValidProfile(String profileUsername, String currentUsername) {
        var optionalProfile = profileRepository.findByUsername(profileUsername, currentUsername);

        if (optionalProfile.isEmpty()) {
            log.info("Profile not found: [profileUsername='{}']", profileUsername);
            throw notFound("Profile not found");
        }

        return optionalProfile.get();
    }

    @Override
    public FollowProfileResponse followProfile(FollowProfileRequest request) {
        Profile profile = getValidProfile(request.getUsername(), request.getCurrentUsername());
        boolean isFollow = profileRepository.followProfile(profile.getUsername(), request.getCurrentUsername());
        return new FollowProfileResponse(profileMapper.apply(profile.withFollowing(isFollow)));
    }

    @Override
    public UnfollowProfileResponse unfollowProfile(UnfollowProfileRequest request) {
        Profile profile = getValidProfile(request.getUsername(), request.getCurrentUsername());
        boolean isFollow = profileRepository.unfollowProfile(profile.getUsername(), request.getCurrentUsername());
        return new UnfollowProfileResponse(profileMapper.apply(profile.withFollowing(!isFollow)));
    }
}
