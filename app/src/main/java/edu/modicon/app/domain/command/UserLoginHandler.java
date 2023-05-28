package edu.modicon.app.domain.command;

import edu.modicon.app.application.request.UserLoginRequest;
import edu.modicon.app.application.response.UserLoginResponse;
import edu.modicon.app.domain.mapper.UserMapper;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.UserRepository;
import edu.modicon.app.infrastructure.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static edu.modicon.app.application.dto.ApiException.notFound;
import static edu.modicon.app.application.dto.ApiException.unauthorized;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserLoginHandler implements CommandHandler<UserLoginResponse, UserLoginRequest> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public UserLoginResponse handle(UserLoginRequest command) {
        log.info("Start login user: [request='{}']", command);

        Optional<User> byEmail = userRepository.findByEmail(command.getEmail());
        if (byEmail.isEmpty()) {
            log.info("User not found: [request='{}']", command);
            throw notFound("User not found");
        }

        User user = byEmail.get();
        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            log.info("Wrong password: [request='{}']", command);
            throw unauthorized("Wrong password");
        }

        log.info("Successfully login user: [userDto='{}']", user);
        return new UserLoginResponse(userMapper.apply(user));
    }
}
