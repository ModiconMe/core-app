package edu.modicon.app.domain.command;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.request.UserUpdateRequest;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import edu.modicon.app.infrastructure.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserUpdateHandlerTest extends BaseTest {

    private final UserUpdateHandler handler;

    public UserUpdateHandlerTest(UserUpdateHandler handler) {
        this.handler = handler;
    }

    @Test
    void shouldUpdateUser(@Autowired JwtUtils jwtUtils) {
        // given
        UserUpdateRequest request = new UserUpdateRequest(
                "newTest",
                "newTest@mail.com",
                "newPassword",
                "newBio",
                "newImage",
                "test1@mail.com"
        );

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(AppUserDetails.ofUsername(request.getEmail()));

        // then
        assertThat(response.username()).isEqualTo(request.getUsername());
        assertThat(response.email()).isEqualTo(request.getEmail());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isEqualTo(request.getBio());
        assertThat(response.image()).isEqualTo(request.getImage());
    }

    @Test
    void shouldUpdateUser_withOldAndNullAndEmptyValProvided(@Autowired JwtUtils jwtUtils) {
        // given
        UserUpdateRequest request = new UserUpdateRequest(
                "test1",
                "newTest@mail.com",
                "newPassword",
                " ",
                null,
                "test1@mail.com"
        );

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(AppUserDetails.ofUsername(request.getEmail()));

        // then
        assertThat(response.username()).isEqualTo(request.getUsername());
        assertThat(response.email()).isEqualTo(request.getEmail());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
    }

    @Test
    void shouldThrow_whenUpdateUser_withExistsEmail() {
        // given
        UserUpdateRequest request = new UserUpdateRequest(
                "newTest",
                "test2@mail.com",
                "newPassword",
                "newBio",
                "newImage",
                "test1@mail.com"
        );

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Duplicate email");
    }

    @Test
    void shouldThrow_whenUpdateUser_withExistsUsername() {
        // given
        UserUpdateRequest request = new UserUpdateRequest(
                "test2",
                "newTest@mail.com",
                "newPassword",
                "newBio",
                "newImage",
                "test1@mail.com"
        );

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Duplicate username");
    }
}
