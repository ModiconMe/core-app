package edu.modicon.app.application.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.modicon.app.application.response.UserRegistrationResponse;
import edu.modicon.app.infrastructure.bus.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeName("user")
public class UserRegistrationRequest implements Command<UserRegistrationResponse> {

    @NotEmpty
    @Size(min = 1, max = 128)
    private final String username;

    @Email
    @Size(min = 1, max = 128)
    private final String email;

    @NotEmpty
    @Size(min = 8, max = 64)
    private final String password;

    @JsonCreator
    public UserRegistrationRequest(
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
               "username='" + username + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
