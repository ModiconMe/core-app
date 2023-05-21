package edu.modicon.app.infrastructure.repository;

import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.UserRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static edu.modicon.app.infrastructure.repository.BaseDao.ParamBuilder.getParameterOf;
import static edu.modicon.app.infrastructure.repository.BaseDao.ParamBuilder.parameters;

@Repository
public class JdbcUserRepository extends BaseDao implements UserRepository {

    public JdbcUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<User> findById(Long id) {
        return single(
                Sql.User.FIND_BY_ID,
                getParameterOf(PARAM_ID, id),
                new BeanPropertyRowMapper<>(User.class)
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return single(
                Sql.User.FIND_BY_EMAIL,
                getParameterOf("email", email),
                new BeanPropertyRowMapper<>(User.class)
        );
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return single(
                Sql.User.FIND_BY_USERNAME,
                getParameterOf("username", username),
                new BeanPropertyRowMapper<>(User.class)
        );
    }

    @Override
    public Long save(User user) {
        SqlParameterSource parameters = parameters(User.class)
                .add("email", user.getEmail())
                .add("username", user.getUsername())
                .add("password", user.getPassword())
                .add("image", user.getImage())
                .add("bio", user.getBio())
                .build();
        return insert(Sql.User.SAVE, parameters);
    }

}
