package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.dto.UserLoginRequest;
import edu.modicon.app.application.dto.UserRegistrationRequest;
import edu.modicon.app.domain.mapper.UserMapper;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.UserRepository;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import edu.modicon.app.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static edu.modicon.app.application.dto.ApiException.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserDto login(UserLoginRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        if (byEmail.isEmpty()) {
            log.info("User not found: [request='{}']", request);
            throw notFound("User not found", request);
        }

        User user = byEmail.get();
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.info("Wrong password: [request='{}']", request);
            throw unauthorized("Wrong password", request);
        }

        AppUserDetails userDetails = AppUserDetails.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        String jwtToken = jwtUtils.generateAccessToken(userDetails);

        return userMapper.apply(user).withToken(jwtToken);
    }

    @Override
    public UserDto registration(UserRegistrationRequest request) {
        log.info("Start register user: [request='{}']", request);

        if (userRepository.findByEmail(request.email()).isPresent()) {
            log.info("User already exist: [request='{}']", request);
            throw unprocessableEntity("User already exist", request);
        }

        String password = request.password();
        if (!StringUtils.hasText(password)) {
            log.info("Empty password: [request='{}']", request);
            throw unprocessableEntity("Empty password", request);
        }

        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(password))
                .build();
        Long userId = userRepository.save(user);

        Optional<UserDto> userDto = userRepository.findById(userId).map(userMapper);
        if (userDto.isEmpty()) {
            log.info("User not found: [request='{}', userId='{}']", request, userId);
            throw notFound("User not found", request, userId);
        }

        UserDto response = userDto.get();
        log.info("Successfully register user: [userDto='{}']", response);
        return response;
    }

}
