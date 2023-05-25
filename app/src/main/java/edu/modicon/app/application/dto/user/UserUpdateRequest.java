package edu.modicon.app.application.dto.user;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.With;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.util.StringUtils.hasText;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeName("user")
public class UserUpdateRequest {

    @Size(min = 1, max = 128)
    private final String username;

    @Email
    @Size(min = 1, max = 128)
    private final String email;

    @Size(min = 8, max = 64)
    private final String password;

    private final String bio;

    private final String image;

    @With
    private final String currentUsername;

    @JsonCreator
    public UserUpdateRequest(
            @JsonProperty("username") String username,
            @JsonProperty("username") String email,
            @JsonProperty("username") String password,
            @JsonProperty("username") String bio,
            @JsonProperty("username") String image,
            String currentUsername
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.image = image;
        this.currentUsername = currentUsername;
    }

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
