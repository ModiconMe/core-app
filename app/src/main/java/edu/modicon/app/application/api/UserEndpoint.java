package edu.modicon.app.application.api;

import edu.modicon.app.application.request.UserLoginRequest;
import edu.modicon.app.application.request.UserRegistrationRequest;
import edu.modicon.app.application.request.UserUpdateRequest;
import edu.modicon.app.application.response.GetCurrentUserResponse;
import edu.modicon.app.application.response.UserLoginResponse;
import edu.modicon.app.application.response.UserRegistrationResponse;
import edu.modicon.app.application.response.UserUpdateResponse;
import edu.modicon.app.domain.constant.Constant;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserEndpoint {
    @PostMapping(Constant.Endpoint.USERS)
    UserRegistrationResponse register(@RequestBody @Valid UserRegistrationRequest command);

    @PostMapping(Constant.Endpoint.USERS + "/login")
    UserLoginResponse login(@RequestBody @Valid UserLoginRequest command);

    @GetMapping(Constant.Endpoint.USER)
    GetCurrentUserResponse getUser();

    @PutMapping(Constant.Endpoint.USER)
    UserUpdateResponse updateUser(@Valid @RequestBody UserUpdateRequest command);
}
