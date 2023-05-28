package edu.modicon.app.application.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.modicon.app.application.response.UserLoginResponse;
import edu.modicon.app.infrastructure.bus.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeName("user")
public class UserLoginRequest implements Command<UserLoginResponse> {

    @Email @Size(min = 1, max = 128)
    private final String email;
    @NotEmpty @Size(min = 8, max = 64)
    private final String password;

    @JsonCreator
    public UserLoginRequest(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password
    ) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
               "email='" + email + '\'' +
               '}';
    }
}
