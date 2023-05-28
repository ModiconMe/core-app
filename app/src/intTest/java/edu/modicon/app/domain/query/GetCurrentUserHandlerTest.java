package edu.modicon.app.domain.query;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.request.GetCurrentUserRequest;
import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import edu.modicon.app.infrastructure.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetCurrentUserHandlerTest extends BaseTest {

    private final GetCurrentUserHandler handler;

    public GetCurrentUserHandlerTest(GetCurrentUserHandler handler) {
        this.handler = handler;
    }

    @Test
    void shouldGetCurrentUser(@Autowired JwtUtils jwtUtils) {
        // given
        AppUserDetails user = AppUserDetails.ofUsername("test1@mail.com");
        GetCurrentUserRequest request = new GetCurrentUserRequest(user.getUsername());

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(user);

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.email()).isEqualTo(request.getEmail());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
    }

    @Test
    void shouldThrow_whenGetCurrent_withWrongEmail() {
        // given
        AppUserDetails user = AppUserDetails.ofUsername("not_exist1@mail.com");
        GetCurrentUserRequest request = new GetCurrentUserRequest(user.getUsername());

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("User not found");
    }

}
