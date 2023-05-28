package edu.modicon.app.domain.command;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.request.UserRegistrationRequest;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import edu.modicon.app.infrastructure.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserRegistrationHandlerTest extends BaseTest {

    private final UserRegistrationHandler handler;

    public UserRegistrationHandlerTest(UserRegistrationHandler handler) {
        this.handler = handler;
    }

    @Test
    void shouldRegisterUser(@Autowired JwtUtils jwtUtils) {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                FAKER.name().username(),
                FAKER.internet().emailAddress(),
                FAKER.random().hex(8)
        );

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(AppUserDetails.ofUsername(request.getEmail()));

        // then
        assertThat(response.username()).isEqualTo(request.getUsername());
        assertThat(response.email()).isEqualTo(request.getEmail());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isNull();
        assertThat(response.image()).isNull();
    }

    @Test
    void shouldThrow_whenRegisterUser_withNullUsername() {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                null,
                FAKER.internet().emailAddress(),
                FAKER.random().hex(8)
        );

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("null value in column \"username\" of relation \"users\" violates not-null constraint");
    }

    @Test
    void shouldThrow_whenRegisterUser_withEmptyUsername() {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                "  ",
                FAKER.internet().emailAddress(),
                FAKER.random().hex(8)
        );

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("new row for relation \"users\" violates check constraint \"ck_empty_username\"");
    }

    @Test
    void shouldThrow_whenRegisterUser_withNullEmail() {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                FAKER.name().username(),
                null,
                FAKER.random().hex(8)
        );

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("null value in column \"email\" of relation \"users\" violates not-null constraint");
    }

    @Test
    void shouldThrow_whenRegisterUser_withEmptyEmail() {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                FAKER.name().username(),
                "  ",
                FAKER.random().hex(8)
        );

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("new row for relation \"users\" violates check constraint \"ck_empty_email\"");
    }

    @Test
    void shouldThrow_whenRegisterUser_withNullPassword() {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                FAKER.name().username(),
                FAKER.internet().emailAddress(),
                null
        );

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Empty password");
    }

    @Test
    void shouldThrow_whenRegisterUser_withEmptyPassword() {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                FAKER.name().username(),
                FAKER.internet().emailAddress(),
                "  "
        );

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Empty password");
    }
}
