package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.dto.UserLoginRequest;
import edu.modicon.app.application.dto.UserRegistrationRequest;

public interface UserService {

    UserDto login(UserLoginRequest request);

    UserDto registration(UserRegistrationRequest request);

}
