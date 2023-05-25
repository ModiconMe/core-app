package edu.modicon.app.application.dto.profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProfileDto(
        @NotEmpty @Size(min = 1, max = 128) String username,
        String bio,
        String image,
        boolean following
) {
}
