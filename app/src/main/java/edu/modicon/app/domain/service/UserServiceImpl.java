package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.dto.UserLoginRequest;
import edu.modicon.app.application.dto.UserRegistrationRequest;
import edu.modicon.app.application.dto.UserUpdateRequest;
import edu.modicon.app.domain.mapper.UserMapper;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.UserRepository;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import edu.modicon.app.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static edu.modicon.app.application.dto.ApiException.*;
import static org.springframework.util.StringUtils.hasText;

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
            throw notFound("User not found");
        }

        User user = byEmail.get();
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.info("Wrong password: [request='{}']", request);
            throw unauthorized("Wrong password");
        }

        String jwtToken = jwtUtils.generateAccessToken(AppUserDetails.fromUser(user));

        return userMapper.apply(user).withToken(jwtToken);
    }

    @Override
    public UserDto registration(UserRegistrationRequest request) {
        log.info("Start register user: [request='{}']", request);

        if (userRepository.findByEmail(request.email()).isPresent()) {
            log.info("User already exist: [request='{}']", request);
            throw unprocessableEntity("User already exist");
        }

        String password = request.password();
        if (!hasText(password)) {
            log.info("Empty password: [request='{}']", request);
            throw unprocessableEntity("Empty password");
        }

        var user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(password));
        Long userId = userRepository.save(user.build());

        UserDto response = getValidResponse(user.id(userId).build());
        log.info("Successfully register user: [userDto='{}']", response);
        return response;
    }

    @Override
    public UserDto currentUser(AppUserDetails request) {
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        if (byEmail.isEmpty()) {
            log.info("User not found: [request='{}']", request);
            throw notFound("User not found", request);
        }

        User user = byEmail.get();
        String jwtToken = jwtUtils.generateAccessToken(AppUserDetails.fromUser(user));

        return userMapper.apply(user).withToken(jwtToken);
    }

    @Override
    public UserDto update(UserUpdateRequest request) {
        log.info("Start update user: [request='{}']", request);

        Optional<User> byEmail = userRepository.findByEmail(request.currentUsername());
        if (byEmail.isEmpty()) {
            log.info("User not found: [request='{}']", request);
            throw notFound("User not found", request);
        }

        User user = byEmail.get();
        checkUsername(request, user);
        checkEmail(request, user);

        User newUser = user.toBuilder()
                .username(request.getValidUsername(user.getUsername()))
                .email(request.getValidEmail(user.getEmail()))
                .password(request.getValidPassword(user.getPassword(), passwordEncoder))
                .bio(request.getValidBio(user.getBio()))
                .image(request.getValidImage(user.getImage()))
                .build();

        userRepository.update(newUser);

        UserDto response = getValidResponse(newUser);
        log.info("Successfully update user: [userDto='{}']", response);
        return response;
    }

    private UserDto getValidResponse(User user) {
        UserDto response = userRepository.findById(user.getId())
                .map(userMapper)
                .orElseThrow(() -> {
                    log.info("User not found: [userId='{}']", user.getId());
                    throw notFound("User not found");
                });

        String jwtToken = jwtUtils.generateAccessToken(AppUserDetails.fromUser(user));

        return response.withToken(jwtToken);
    }

    private void checkUsername(UserUpdateRequest request, User user) {
        if (hasText(request.username()) && !request.username().equals(user.getUsername())) {
            Optional<User> checkUsername = userRepository.findByUsername(request.username())
                    .filter(found -> !found.getId().equals(user.getId()));
            if (checkUsername.isPresent()) {
                log.info("Duplicate username: [request='{}']", request);
                throw unprocessableEntity("Duplicate username");
            }
        }
    }

    private void checkEmail(UserUpdateRequest request, User user) {
        if (hasText(request.email()) && !request.email().equals(user.getEmail())) {
            Optional<User> checkEmail = userRepository.findByEmail(request.email())
                    .filter(found -> !found.getId().equals(user.getId()));
            if (checkEmail.isPresent()) {
                log.info("Duplicate email: [request='{}']", request);
                throw unprocessableEntity("Duplicate email");
            }
        }
    }
}
