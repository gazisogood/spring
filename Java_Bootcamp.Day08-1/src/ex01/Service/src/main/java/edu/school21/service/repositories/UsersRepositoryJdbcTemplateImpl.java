package edu.school21.service.repositories;

import edu.school21.service.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository<User>, RowMapper<User> {
    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource jdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate(jdbcTemplate);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", this, email);
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", this, id);
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", this);
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO users (email) VALUES (?)",
                entity.getEmail());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users SET email = ? WHERE id = ?",
                entity.getEmail(), entity.getId());
    }


    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}
