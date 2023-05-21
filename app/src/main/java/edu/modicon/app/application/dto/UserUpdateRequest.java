package edu.modicon.app.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.With;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.util.StringUtils.hasText;

@JsonRootName("user")
public record UserUpdateRequest(
        @Size(min = 1, max = 128) String username,
        @Email @Size(min = 1, max = 128) String email,
        @Size(min = 8, max = 64) String password,
        String bio,
        String image,
        @With String currentUsername
) {

    @Override
    public String toString() {
        return "UserUpdateRequest{" +
               "username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", bio='" + bio + '\'' +
               ", image='" + image + '\'' +
               '}';
    }

    public String getValidUsername(String oldUsername) {
        return hasText(username) && !username.equals(oldUsername) ? username : oldUsername;
    }

    public String getValidEmail(String oldEmail) {
        return hasText(email) && !email.equals(oldEmail) ? email : oldEmail;
    }

    public String getValidPassword(String oldPassword, PasswordEncoder passwordEncoder) {
        return hasText(password) && !passwordEncoder.matches(password, oldPassword) ? passwordEncoder.encode(password) : oldPassword;
    }

    public String getValidBio(String oldBio) {
        return hasText(bio) && !bio.equals(oldBio) ? bio : oldBio;
    }

    public String getValidImage(String oldImage) {
        return hasText(image) && !image.equals(oldImage) ? image : oldImage;
    }
}
