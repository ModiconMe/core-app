package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.profile.GetProfileRequest;
import edu.modicon.app.application.dto.profile.GetProfileResponse;
import edu.modicon.app.domain.mapper.ProfileMapper;
import edu.modicon.app.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static edu.modicon.app.application.dto.ApiException.notFound;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public GetProfileResponse getProfile(GetProfileRequest request) {
        var optionalProfile = profileRepository.findByUsername(request.getUsername(), request.getCurrentUsername());

        if (optionalProfile.isEmpty()) {
            log.info("Profile not found: [request='{}']", request);
            throw notFound("Profile not found");
        }

        return new GetProfileResponse(profileMapper.apply(optionalProfile.get()));
    }

}
