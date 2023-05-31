package edu.modicon.app.domain.query;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.dto.profile.GetProfileRequest;
import edu.modicon.app.application.dto.profile.ProfileDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetProfileHandlerTest extends BaseTest {

    private final GetProfileHandler handler;

    public GetProfileHandlerTest(GetProfileHandler handler) {
        this.handler = handler;
    }

    @Test
    void shouldGetProfile() {
        // given
        GetProfileRequest request = new GetProfileRequest("test1", "test2");

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
        assertThat(response.following()).isEqualTo(true);
    }

    @Test
    void shouldGetProfile_withNullCurrentUsername() {
        // given
        GetProfileRequest request = new GetProfileRequest("test1", null);

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
        assertThat(response.following()).isEqualTo(false);
    }

    @Test
    void shouldThrow_whenGetProfile_withWrongUsername() {
        // given
        GetProfileRequest request = new GetProfileRequest("test123", "test2");

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }

    @Test
    void shouldThrow_whenGetProfile_withNullUsername() {
        // given
        GetProfileRequest request = new GetProfileRequest(null, "test2");

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }
}
