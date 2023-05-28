package edu.modicon.app.application.request;

import edu.modicon.app.application.response.GetCurrentUserResponse;
import edu.modicon.app.infrastructure.bus.Query;
import lombok.Getter;

@Getter
public class GetCurrentUserRequest implements Query<GetCurrentUserResponse> {

    private final String email;

    public GetCurrentUserRequest(String currentUsername) {
        this.email = currentUsername;
    }
}
