package edu.modicon.app.domain.command;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.request.UserUpdateRequest;
import edu.modicon.app.application.response.UserUpdateResponse;
import edu.modicon.app.domain.mapper.UserMapper;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.UserRepository;
import edu.modicon.app.infrastructure.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static edu.modicon.app.application.dto.ApiException.notFound;
import static edu.modicon.app.application.dto.ApiException.unprocessableEntity;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserUpdateHandler implements CommandHandler<UserUpdateResponse, UserUpdateRequest> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserUpdateResponse handle(UserUpdateRequest command) {
        log.info("Start update user: [request='{}']", command);

        Optional<User> byEmail = userRepository.findByEmail(command.getCurrentUsername());
        if (byEmail.isEmpty()) {
            log.info("User not found: [request='{}']", command);
            throw notFound("User not found", command);
        }

        User user = byEmail.get();
        checkUsername(command, user);
        checkEmail(command, user);

        User newUser = user.toBuilder()
                .username(command.getValidUsername(user.getUsername()))
                .email(command.getValidEmail(user.getEmail()))
                .password(command.getValidPassword(user.getPassword(), passwordEncoder))
                .bio(command.getValidBio(user.getBio()))
                .image(command.getValidImage(user.getImage()))
                .build();

        userRepository.update(newUser);

        UserDto dto = userMapper.apply(newUser);
        log.info("Successfully update user: [userDto='{}']", dto);
        return new UserUpdateResponse(dto);
    }

    private void checkUsername(UserUpdateRequest request, User user) {
        if (hasText(request.getUsername()) && !request.getUsername().equals(user.getUsername())) {
            Optional<User> checkUsername = userRepository.findByUsername(request.getUsername())
                    .filter(found -> !found.getId().equals(user.getId()));
            if (checkUsername.isPresent()) {
                log.info("Duplicate username: [request='{}']", request);
                throw unprocessableEntity("Duplicate username");
            }
        }
    }

    private void checkEmail(UserUpdateRequest request, User user) {
        if (hasText(request.getEmail()) && !request.getEmail().equals(user.getEmail())) {
            Optional<User> checkEmail = userRepository.findByEmail(request.getEmail())
                    .filter(found -> !found.getId().equals(user.getId()));
            if (checkEmail.isPresent()) {
                log.info("Duplicate email: [request='{}']", request);
                throw unprocessableEntity("Duplicate email");
            }
        }
    }
}
