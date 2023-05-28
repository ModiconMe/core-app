package edu.modicon.app.application.api.controller;

import edu.modicon.app.application.api.BaseController;
import edu.modicon.app.application.api.UserEndpoint;
import edu.modicon.app.application.request.GetCurrentUserRequest;
import edu.modicon.app.application.request.UserLoginRequest;
import edu.modicon.app.application.request.UserRegistrationRequest;
import edu.modicon.app.application.request.UserUpdateRequest;
import edu.modicon.app.application.response.GetCurrentUserResponse;
import edu.modicon.app.application.response.UserLoginResponse;
import edu.modicon.app.application.response.UserRegistrationResponse;
import edu.modicon.app.application.response.UserUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController extends BaseController implements UserEndpoint {

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest command) {
        return bus.executeCommand(command);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest command) {
        return bus.executeCommand(command);
    }

    @Override
    public GetCurrentUserResponse getUser() {
        return bus.executeQuery(new GetCurrentUserRequest(getCurrentUser().getUsername()));
    }

    @Override
    public UserUpdateResponse updateUser(UserUpdateRequest command) {
        return bus.executeCommand(command.withCurrentUsername(getCurrentUser().getUsername()));
    }
}
