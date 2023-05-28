package edu.modicon.app.application.response;

import edu.modicon.app.application.dto.UserDto;
import lombok.Value;

@Value
public class UserLoginResponse {
    UserDto user;
}
