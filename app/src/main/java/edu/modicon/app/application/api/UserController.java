package edu.modicon.app.application.api;

import edu.modicon.app.application.dto.user.UserDto;
import edu.modicon.app.application.dto.user.UserUpdateRequest;
import edu.modicon.app.application.dto.user.UserUpdateResponse;
import edu.modicon.app.domain.constant.Constant;
import edu.modicon.app.domain.service.UserService;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constant.Endpoint.USER)
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDto getCurrentUser(@AuthenticationPrincipal AppUserDetails user) {
        return userService.currentUser(user);
    }

    public UserUpdateResponse update(@Valid @RequestBody UserUpdateRequest request, @AuthenticationPrincipal AppUserDetails user) {
        return userService.update(request.withCurrentUsername(user.getUsername()));
    }
}
