package com.example.tgbotecho.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EchoBotDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EchoBotDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<Integer> updateAndGetMsgCountForUser(String userName) {
        namedParameterJdbcTemplate.update("UPDATE users SET count = count + 1 WHERE user_name = :username",
                new MapSqlParameterSource().addValue("username", userName));
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                            " SELECT count FROM users WHERE user_name = :username",
                    new MapSqlParameterSource().addValue("username", userName),
                    (rs, rn) -> rs.getInt("count")));
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    public void addNewUser(String userName) {
        namedParameterJdbcTemplate.update("INSERT INTO users VALUES(:username, 0)",
                new MapSqlParameterSource().addValue("username", userName));
    }
}
