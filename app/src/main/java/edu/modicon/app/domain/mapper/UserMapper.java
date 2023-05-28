package edu.modicon.app.domain.mapper;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import edu.modicon.app.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class UserMapper implements Function<User, UserDto> {

    private final JwtUtils jwtUtils;

    @Override
    public UserDto apply(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .token(jwtUtils.generateAccessToken(AppUserDetails.fromUser(user)))
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
