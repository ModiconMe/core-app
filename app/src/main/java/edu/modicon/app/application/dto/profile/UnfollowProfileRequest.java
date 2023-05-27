package edu.modicon.app.application.dto.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.With;

@Getter
public class UnfollowProfileRequest {

    private final String username;

    @With
    private final String currentUsername;

    @JsonCreator
    public UnfollowProfileRequest(
            @JsonProperty("username") String username,
            String currentUsername
    ) {
        this.username = username;
        this.currentUsername = currentUsername;
    }

}
