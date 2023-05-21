package edu.modicon.app.domain.service;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.dto.UserLoginRequest;
import edu.modicon.app.application.dto.UserRegistrationRequest;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import edu.modicon.app.infrastructure.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest extends BaseTest {

    private final UserService userService;

    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void shouldRegisterUser() {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                FAKER.name().username(),
                FAKER.internet().emailAddress(),
                FAKER.random().hex(8)
        );

        // when
        UserDto response = userService.registration(request);

        // then
        assertThat(response.username()).isEqualTo(request.username());
        assertThat(response.email()).isEqualTo(request.email());
        assertThat(response.token()).isEqualTo("TOKEN");
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
        assertThatThrownBy(() -> userService.registration(request))
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
        assertThatThrownBy(() -> userService.registration(request))
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
        assertThatThrownBy(() -> userService.registration(request))
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
        assertThatThrownBy(() -> userService.registration(request))
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
        assertThatThrownBy(() -> userService.registration(request))
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
        assertThatThrownBy(() -> userService.registration(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Empty password");
    }

    @Test
    void shouldLoginUser(@Autowired JwtUtils jwtUtils) {
        // given
        UserLoginRequest request = new UserLoginRequest("test1@mail.com", "password1");

        // when
        UserDto response = userService.login(request);

        AppUserDetails build = AppUserDetails.builder()
                .email(request.email())
                .build();
        String token = jwtUtils.generateAccessToken(build);

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.email()).isEqualTo(request.email());
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
        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void shouldThrow_whenLoginUser_withWrongPassword() {
        // given
        UserLoginRequest request = new UserLoginRequest("test1@mail.com", "not_exist1");

        // when
        // then
        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Wrong password");
    }

    @Test
    void shouldGetCurrentUser(@Autowired JwtUtils jwtUtils) {
        // given
        AppUserDetails request = AppUserDetails.builder()
                .email("test1@mail.com")
                .build();

        // when
        UserDto response = userService.currentUser(request);

        String token = jwtUtils.generateAccessToken(request);

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.email()).isEqualTo(request.email());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
    }

    @Test
    void shouldThrow_whenGetCurrent_withWrongEmail() {
        // given
        AppUserDetails request = AppUserDetails.builder()
                .email("not_exist1@mail.com")
                .build();

        // when
        // then
        assertThatThrownBy(() -> userService.currentUser(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("User not found");
    }
}
