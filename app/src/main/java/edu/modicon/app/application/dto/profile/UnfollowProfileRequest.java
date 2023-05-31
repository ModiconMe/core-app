package edu.modicon.app.application.dto.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.modicon.app.infrastructure.bus.Command;
import lombok.Getter;
import lombok.With;

@Getter
public class UnfollowProfileRequest implements Command<UnfollowProfileResponse> {

    private final String username;
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
