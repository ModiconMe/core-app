package edu.modicon.app.domain.query;

import edu.modicon.app.application.dto.profile.GetProfileRequest;
import edu.modicon.app.application.dto.profile.GetProfileResponse;
import edu.modicon.app.domain.mapper.ProfileMapper;
import edu.modicon.app.domain.model.Profile;
import edu.modicon.app.domain.repository.ProfileRepository;
import edu.modicon.app.infrastructure.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static edu.modicon.app.application.dto.ApiException.notFound;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetProfileHandler implements QueryHandler<GetProfileResponse, GetProfileRequest> {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Transactional(readOnly = true)
    @Override
    public GetProfileResponse handle(GetProfileRequest query) {
        return new GetProfileResponse(
                profileMapper.apply(
                        getValidProfile(query.getUsername(), query.getCurrentUsername())
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
}
