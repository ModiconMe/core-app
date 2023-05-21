package edu.modicon.app.domain.mapper;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
