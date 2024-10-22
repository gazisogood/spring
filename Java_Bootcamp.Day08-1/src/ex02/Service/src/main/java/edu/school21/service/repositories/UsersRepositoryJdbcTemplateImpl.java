package edu.school21.service.repositories;

import edu.school21.service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository<User>, RowMapper<User> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("jdbcTemplateDataSource") DataSource jdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate(jdbcTemplate);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT id, email, password FROM users WHERE email = ?", this, email);
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT id, email, password FROM users WHERE id = ?", this, id);
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT id, email, password FROM users", this);
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO users (email, password) VALUES (?, ?)",
                entity.getEmail(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users SET email = ?, password = ? WHERE id = ?",
                entity.getEmail(), entity.getPassword(), entity.getId());
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
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
