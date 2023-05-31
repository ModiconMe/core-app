package edu.modicon.app.domain.command;

import edu.modicon.app.application.dto.profile.FollowProfileRequest;
import edu.modicon.app.application.dto.profile.FollowProfileResponse;
import edu.modicon.app.domain.mapper.ProfileMapper;
import edu.modicon.app.domain.model.Profile;
import edu.modicon.app.domain.repository.ProfileRepository;
import edu.modicon.app.infrastructure.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static edu.modicon.app.application.dto.ApiException.notFound;

@Slf4j
@RequiredArgsConstructor
@Component
public class FollowProfileHandler implements CommandHandler<FollowProfileResponse, FollowProfileRequest> {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Transactional
    @Override
    public FollowProfileResponse handle(FollowProfileRequest command) {
        Profile profile = getValidProfile(command.getUsername(), command.getCurrentUsername());
        boolean isFollow = profileRepository.followProfile(profile.getUsername(), command.getCurrentUsername());
        return new FollowProfileResponse(profileMapper.apply(profile.withFollowing(isFollow)));
    }

    private Profile getValidProfile(String profileUsername, String currentUsername) {
        var optionalProfile = profileRepository.findByUsername(profileUsername, currentUsername);

        if (optionalProfile.isEmpty()) {
            log.info("Profile not found: [profileUsername='{}']", profileUsername);
            throw notFound("Profile not found");
        }

        return optionalProfile.get();
    }
}
