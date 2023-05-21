package edu.modicon.app.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@JsonRootName("user")
public record UserLoginRequest(
        @Email @Size(min = 1, max = 128) String email,
        @NotEmpty @Size(min = 8, max = 64) String password
) {

    @Override
    public String toString() {
        return "UserLoginRequest{" +
               "email='" + email + '\'' +
               '}';
    }
}
