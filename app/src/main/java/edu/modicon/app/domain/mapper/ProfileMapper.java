package edu.modicon.app.domain.mapper;

import edu.modicon.app.application.dto.profile.ProfileDto;
import edu.modicon.app.domain.model.Profile;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProfileMapper implements Function<Profile, ProfileDto> {
    @Override
    public ProfileDto apply(Profile profile) {
        return new ProfileDto(profile.getUsername(),
                profile.getBio(), profile.getImage(), profile.isFollowing());
    }
}
