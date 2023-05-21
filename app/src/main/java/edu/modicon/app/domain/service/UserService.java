package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.dto.UserLoginRequest;
import edu.modicon.app.application.dto.UserRegistrationRequest;
import edu.modicon.app.application.dto.UserUpdateRequest;
import edu.modicon.app.infrastructure.security.AppUserDetails;

public interface UserService {

    UserDto login(UserLoginRequest request);

    UserDto registration(UserRegistrationRequest request);

    UserDto currentUser(AppUserDetails request);

    UserDto update(UserUpdateRequest request);
}
