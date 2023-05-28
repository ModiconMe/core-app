package edu.modicon.app.domain.command;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.request.UserLoginRequest;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import edu.modicon.app.infrastructure.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserLoginHandlerTest extends BaseTest {

    private final UserLoginHandler handler;

    public UserLoginHandlerTest(UserLoginHandler handler) {
        this.handler = handler;
    }

    @Test
    void shouldLoginUser(@Autowired JwtUtils jwtUtils) {
        // given
        UserLoginRequest request = new UserLoginRequest("test1@mail.com", "password1");

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(AppUserDetails.ofUsername(request.getEmail()));

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.email()).isEqualTo(request.getEmail());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
    }

    @Test
    void shouldThrow_whenLoginUser_withWrongEmail() {
        // given
        UserLoginRequest request = new UserLoginRequest("not_exist1@mail.com", "password1");

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void shouldThrow_whenLoginUser_withWrongPassword() {
        // given
        UserLoginRequest request = new UserLoginRequest("test1@mail.com", "not_exist1");

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Wrong password");
    }
}
