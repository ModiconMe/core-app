package edu.modicon.app.domain.command;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.dto.profile.ProfileDto;
import edu.modicon.app.application.dto.profile.UnfollowProfileRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UnfollowProfileHandlerTest extends BaseTest {

    private final UnfollowProfileHandler handler;

    public UnfollowProfileHandlerTest(UnfollowProfileHandler handler) {
        this.handler = handler;
    }

    @Test
    void shouldUnfollowProfile() {
        // given
        UnfollowProfileRequest request = new UnfollowProfileRequest("test1", "test2");

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo(request.getUsername());
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
        assertThat(response.following()).isEqualTo(false);
    }

    @Test
    void shouldThrow_whenUnfollowProfile_withWrongUsername() {
        // given
        UnfollowProfileRequest request = new UnfollowProfileRequest("test123", "test2");

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }
}
