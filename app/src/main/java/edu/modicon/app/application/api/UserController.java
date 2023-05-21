package edu.modicon.app.application.api;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.dto.UserLoginRequest;
import edu.modicon.app.application.dto.UserRegistrationRequest;
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
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public UserDto login(@Valid @RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @PostMapping
    public UserDto registration(@Valid @RequestBody UserRegistrationRequest request) {
        return userService.registration(request);
    }
}
