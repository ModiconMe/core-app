package edu.modicon.app.infrastructure.repository;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static edu.modicon.app.application.dto.ApiException.unprocessableEntity;

abstract public class BaseDao {

    protected static final String PARAM_ID = "id";

    private static final String[] KEY = new String[]{PARAM_ID};

    protected final NamedParameterJdbcTemplate jdbcTemplate;

    protected BaseDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected <T> Optional<T> single(String sql, SqlParameterSource params, RowMapper<T> rowMapper) {
        return Optional.ofNullable(first(list(sql, params, rowMapper)));
    }

    protected <T> Optional<T> single(String sql, SqlParameterSource params, Class<T> clazz) {
        return Optional.ofNullable(first(list(sql, params, clazz)));
    }

    protected <T> Optional<T> single(String sql, SqlParameterSource params, ResultSetExtractor<T> resultSet) {
        return Optional.ofNullable(jdbcTemplate.query(sql, params, resultSet));
    }

    protected long count(String sql, SqlParameterSource params) {
        Long value = jdbcTemplate.queryForObject(sql, params, Long.class);
        return value != null ? value : 0L;
    }

    protected boolean isExist(String sql, SqlParameterSource paramSource) {
        return count(sql, paramSource) > 0;
    }

    private static <T> T first(List<T> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        return items.get(0);
    }

    protected <T> List<T> list(String sql, SqlParameterSource params, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    protected <T> List<T> list(String sql, SqlParameterSource params, Class<T> clazz) {
        return jdbcTemplate.queryForList(sql, params, clazz);
    }


    protected void updateOneRow(String sql, SqlParameterSource paramSource) {
        int rowCount = jdbcTemplate.update(
                sql,
                paramSource
        );

        if (rowCount != 1) {
            throw new IncorrectResultSizeDataAccessException(1, rowCount);
        }
    }

    protected int update(String sql) {
        return update(sql, null);
    }

    protected int update(String sql, SqlParameterSource params) {
        if (params == null) {
            return jdbcTemplate.update(sql, Collections.emptyMap());
        } else {
            return jdbcTemplate.update(sql, params);
        }
    }

    protected void batchUpdate(String sql, SqlParameterSource[] params) {
        jdbcTemplate.batchUpdate(sql, params);
    }

    protected <T> T selectOneObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) {
        return jdbcTemplate.queryForObject(sql, paramSource, rowMapper);
    }

    protected long insert(String sql, SqlParameterSource params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, KEY);
        if (keyHolder.getKey() == null) {
            throw unprocessableEntity("Error get key of created session");
        }
        return keyHolder.getKey().longValue();
    }

    protected long insert(String sql, SqlParameterSource params, String idName) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{idName});
        if (keyHolder.getKey() == null) {
            throw unprocessableEntity("Error get key of created session");
        }
        return keyHolder.getKey().longValue();
    }

    protected static Long getNullLong(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getObject(columnLabel) != null ? rs.getLong(columnLabel) : null;
    }

    protected static Integer getNullInt(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getObject(columnLabel) != null ? rs.getInt(columnLabel) : null;
    }

    private static <T> Map<String, T> mapOf(String key, T value) {
        return Collections.singletonMap(key, value);
    }

    protected BeanPropertyRowMapper<?> getRowMapper(Class<?> clazz) {
        return BeanPropertyRowMapper.newInstance(clazz);
    }

    protected static class ParamBuilder {

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        static List<String> fields;

        static SqlParameterSource getParameterOf(final String key, final Object value) {
            return new MapSqlParameterSource(key, value);
        }

        static ParamBuilder parameters(final Class<?> clazz) {
            fields = Arrays.stream(clazz.getDeclaredFields())
                    .map(Field::getName)
                    .toList();
            return new ParamBuilder();
        }

        ParamBuilder add(final String key, final Object value) {
            if
                (fields.contains(key)) parameters.addValue(key, value);
            else
                throw unprocessableEntity("Field not found", key);
            return this;
        }

        SqlParameterSource build() {
            return parameters;
        }

    }
}
