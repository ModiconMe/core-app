package edu.modicon.app.domain.command;

import edu.modicon.app.application.dto.UserDto;
import edu.modicon.app.application.request.UserRegistrationRequest;
import edu.modicon.app.application.response.UserRegistrationResponse;
import edu.modicon.app.domain.mapper.UserMapper;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.UserRepository;
import edu.modicon.app.infrastructure.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static edu.modicon.app.application.dto.ApiException.unprocessableEntity;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserRegistrationHandler implements CommandHandler<UserRegistrationResponse, UserRegistrationRequest> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserRegistrationResponse handle(UserRegistrationRequest command) {
        log.info("Start register user: [request='{}']", command);

        if (userRepository.findByEmail(command.getEmail()).isPresent()) {
            log.info("User already exist: [request='{}']", command);
            throw unprocessableEntity("User already exist");
        }

        String password = command.getPassword();
        if (!hasText(password)) {
            log.info("Empty password: [request='{}']", command);
            throw unprocessableEntity("Empty password");
        }

        var user = User.builder()
                .email(command.getEmail())
                .username(command.getUsername())
                .password(passwordEncoder.encode(password));
        Long userId = userRepository.save(user.build());

        UserDto dto = userMapper.apply(user.id(userId).build());
        log.info("Successfully register user: [userDto='{}']", dto);
        return new UserRegistrationResponse(dto);
    }
}
