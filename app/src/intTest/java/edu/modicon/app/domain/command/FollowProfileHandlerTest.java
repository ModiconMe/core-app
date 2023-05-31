package edu.modicon.app.domain.command;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.dto.profile.FollowProfileRequest;
import edu.modicon.app.application.dto.profile.ProfileDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FollowProfileHandlerTest extends BaseTest {

    private final FollowProfileHandler handler;

    public FollowProfileHandlerTest(FollowProfileHandler handler) {
        this.handler = handler;
    }

    @Test
    void shouldFollowProfile() {
        // given
        FollowProfileRequest request = new FollowProfileRequest("test1", "test2");

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo(request.getUsername());
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
        assertThat(response.following()).isEqualTo(true);
    }

    @Test
    void shouldThrow_whenFollowProfile_withWrongUsername() {
        // given
        FollowProfileRequest request = new FollowProfileRequest("test123", "test2");

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }
}
