package edu.modicon.app.application.api;

import edu.modicon.app.application.dto.user.UserLoginRequest;
import edu.modicon.app.application.dto.user.UserLoginResponse;
import edu.modicon.app.application.dto.user.UserRegistrationRequest;
import edu.modicon.app.application.dto.user.UserRegistrationResponse;
import edu.modicon.app.domain.constant.Constant;
import edu.modicon.app.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constant.Endpoint.USERS)
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @PostMapping
    public UserRegistrationResponse registration(@Valid @RequestBody UserRegistrationRequest request) {
        return userService.registration(request);
    }
}
