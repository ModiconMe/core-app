package edu.modicon.app.domain.query;

import edu.modicon.app.application.request.GetCurrentUserRequest;
import edu.modicon.app.application.response.GetCurrentUserResponse;
import edu.modicon.app.domain.mapper.UserMapper;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.UserRepository;
import edu.modicon.app.infrastructure.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static edu.modicon.app.application.dto.ApiException.notFound;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetCurrentUserHandler implements QueryHandler<GetCurrentUserResponse, GetCurrentUserRequest> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public GetCurrentUserResponse handle(GetCurrentUserRequest query) {
        Optional<User> optionalUser = userRepository.findByEmail(query.getEmail());
        if (optionalUser.isEmpty()) {
            log.info("User not found: [request='{}']", query);
            throw notFound("User not found", query);
        }

        User currentUser = optionalUser.get();
        log.info("Current user found: [currentUser='{}'", currentUser);
        return new GetCurrentUserResponse(userMapper.apply(currentUser));
    }
}
