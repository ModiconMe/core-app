package edu.modicon.app.domain.service;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.ApiException;
import edu.modicon.app.application.dto.profile.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileServiceTest extends BaseTest {

    private final ProfileService profileService;

    public ProfileServiceTest(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Test
    void shouldGetProfile() {
        // given
        GetProfileRequest request = new GetProfileRequest("test1", null).withCurrentUsername("test2");

        // when
        ProfileDto response = profileService.getProfile(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
        assertThat(response.following()).isEqualTo(true);
    }

    @Test
    void shouldGetProfile_withNullCurrentUsername() {
        // given
        GetProfileRequest request = new GetProfileRequest("test1", null).withCurrentUsername(null);

        // when
        ProfileDto response = profileService.getProfile(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
        assertThat(response.following()).isEqualTo(false);
    }

    @Test
    void shouldThrow_whenGetProfile_withWrongUsername() {
        // given
        GetProfileRequest request = new GetProfileRequest("test123", null).withCurrentUsername("test2");

        // when
        // then
        assertThatThrownBy(() -> profileService.getProfile(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }

    @Test
    void shouldThrow_whenGetProfile_withNullUsername() {
        // given
        GetProfileRequest request = new GetProfileRequest(null, null).withCurrentUsername("test2");

        // when
        // then
        assertThatThrownBy(() -> profileService.getProfile(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }

    @Test
    void shouldFollowProfile() {
        // given
        FollowProfileRequest request = new FollowProfileRequest("test1", null).withCurrentUsername("test2");

        // when
        ProfileDto response = profileService.followProfile(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
        assertThat(response.following()).isEqualTo(true);
    }

    @Test
    void shouldThrow_whenFollowProfile_withWrongUsername() {
        // given
        FollowProfileRequest request = new FollowProfileRequest("test123", null).withCurrentUsername("test2");

        // when
        // then
        assertThatThrownBy(() -> profileService.followProfile(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }

    @Test
    void shouldUnfollowProfile() {
        // given
        UnfollowProfileRequest request = new UnfollowProfileRequest("test1", null).withCurrentUsername("test2");

        // when
        ProfileDto response = profileService.unfollowProfile(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo("test1");
        assertThat(response.bio()).isEqualTo("bio1");
        assertThat(response.image()).isEqualTo("image1");
        assertThat(response.following()).isEqualTo(false);
    }

    @Test
    void shouldThrow_whenUnfollowProfile_withWrongUsername() {
        // given
        UnfollowProfileRequest request = new UnfollowProfileRequest("test123", null).withCurrentUsername("test2");

        // when
        // then
        assertThatThrownBy(() -> profileService.unfollowProfile(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }

}
