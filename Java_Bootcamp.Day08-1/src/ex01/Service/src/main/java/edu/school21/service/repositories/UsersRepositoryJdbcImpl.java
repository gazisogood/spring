package edu.school21.service.repositories;

import edu.school21.service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcImpl implements UsersRepository<User> {
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private final DataSource dataSource;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("User Email cannot be null.");
        }
        Optional<User> result = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, email FROM users WHERE email = ?")) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = Optional.of(new User(
                            resultSet.getLong(ID),
                            resultSet.getString(EMAIL)
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }
        Optional<User> result = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, email FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = Optional.of(new User(
                            resultSet.getLong(ID),
                            resultSet.getString(EMAIL)
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, email FROM users")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong(ID),
                            resultSet.getString(EMAIL)
                    );
                    result.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(User entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email) VALUES (?)")) {
            statement.setString(1, entity.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to insert user.");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                } else {
                    throw new SQLException("Failed to insert user. no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET email = ? WHERE id = ?")) {
            statement.setString(1, entity.getEmail());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to delete user. No user found with the specified ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
