package edu.modicon.app.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.With;

@Builder
public record UserDto(
        @NotEmpty @Size(min = 1, max = 128) String username,
        @Email @Size(min = 1, max = 128) String email,
        @NotEmpty @With String token,
        String bio,
        String image
) {
}
