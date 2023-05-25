package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.user.*;
import edu.modicon.app.infrastructure.security.AppUserDetails;

public interface UserService {

    UserLoginResponse login(UserLoginRequest request);

    UserRegistrationResponse registration(UserRegistrationRequest request);

    UserDto currentUser(AppUserDetails request);

    UserUpdateResponse update(UserUpdateRequest request);
}
