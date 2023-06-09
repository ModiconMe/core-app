package edu.modicon.app.infrastructure.repository;

import edu.modicon.app.domain.model.Profile;
import edu.modicon.app.domain.repository.ProfileRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static edu.modicon.app.infrastructure.repository.BaseDao.ParamBuilder.getParameterOf;
import static edu.modicon.app.infrastructure.repository.BaseDao.ParamBuilder.parameters;

@Repository
public class JdbcProfileRepository extends BaseDao implements ProfileRepository {

    public JdbcProfileRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Profile> findByUsername(String profileUsername, String currentUsername) {
        SqlParameterSource params = parameters()
                .add("profileUsername", profileUsername)
                .add("currentUsername", currentUsername)
                .build();
        return single(Sql.Profile.FIND_BY_USERNAME, params, new BeanPropertyRowMapper<>(Profile.class));
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return single(Sql.Profile.FIND_BY_ID, getParameterOf(PARAM_ID, id), new BeanPropertyRowMapper<>(Profile.class));
    }

    @Override
    public boolean followProfile(String profileUsername, String currentUsername) {
        SqlParameterSource params = parameters()
                .add("profileUsername", profileUsername)
                .add("currentUsername", currentUsername)
                .build();
        return isExist(Sql.Profile.FOLLOW, params);
    }

    @Override
    public boolean unfollowProfile(String profileUsername, String currentUsername) {
        SqlParameterSource params = parameters()
                .add("profileUsername", profileUsername)
                .add("currentUsername", currentUsername)
                .build();
        return isExist(Sql.Profile.UNFOLLOW, params);
    }
}
