package edu.school21.service.services;

import edu.school21.service.config.TestApplicationConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
public class UsersServiceImplTest {
    private static DataSource dataSource;
    private static UsersService usersServiceJdbcTemplate;

    @BeforeAll
    static void before() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        dataSource = context.getBean("jdbcTemplateDataSource", DataSource.class);
        usersServiceJdbcTemplate = context.getBean("usersServiceImpl", UsersService.class);
    }

    @BeforeEach
    public void init() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS users;");
            st.executeUpdate("CREATE TABLE users(ID INT,\n email VARCHAR(255), password VARCHAR(255));");
            st.executeUpdate("INSERT INTO users VALUES(1, 'hakekett@student.21-school.ru', '12345');");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @AfterAll
    public static void clean() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS Users;");
            st.executeUpdate("CREATE TABLE users(ID INT,\n email VARCHAR(255), password VARCHAR(255));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings =
            {"arboriob@student.21-school.ru", "hakekett@student.21-school.ru", "risahamm@student.21-school.ru"})
    public void isSignedUpTemplate(String email) {
        assertNotNull(usersServiceJdbcTemplate.signUp(email));
    }
}
