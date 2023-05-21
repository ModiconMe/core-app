package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.dto.UserLoginRequest;
import edu.modicon.app.application.dto.UserRegistrationRequest;
import edu.modicon.app.domain.mapper.UserMapper;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static edu.modicon.app.application.dto.ApiException.notFound;
import static edu.modicon.app.application.dto.ApiException.unprocessableEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto login(UserLoginRequest request) {
        return null;
    }

    @Override
    public UserDto registration(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            log.info("User already exist: [request='{}']", request);
            throw unprocessableEntity("User already exist: [request='%s']", request);
        }

        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        Long userId = userRepository.save(user);

        Optional<UserDto> userDto = userRepository.findById(userId).map(userMapper);
        if (userDto.isEmpty()) {
            log.info("User not found: [request='{}', userId='{}']", request, userId);
            throw notFound("User not found: [request='%s', userId='%s']", request, userId);
        }

        return userDto.get();
    }

}
