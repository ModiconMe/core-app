package edu.modicon.app.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserDto(
        @NotEmpty @Size(min = 1, max = 128) String username,
        @Email @Size(min = 1, max = 128) String email,
        @NotEmpty @Size(min = 8, max = 64) String password,
        String bio,
        String image
) {
}
