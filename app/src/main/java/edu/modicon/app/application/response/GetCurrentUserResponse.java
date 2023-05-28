package edu.modicon.app.application.response;

import edu.modicon.app.application.dto.UserDto;
import lombok.Value;

@Value
public class GetCurrentUserResponse {
    UserDto user;
}
