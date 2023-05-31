package edu.modicon.app.application.dto.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.modicon.app.infrastructure.bus.Query;
import lombok.Getter;
import lombok.With;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeName("profile")
public class GetProfileRequest implements Query<GetProfileResponse> {

    private final String username;
    private final String currentUsername;

    @JsonCreator
    public GetProfileRequest(
            @JsonProperty("username") String username,
            String currentUsername
    ) {
        this.username = username;
        this.currentUsername = currentUsername;
    }
}
