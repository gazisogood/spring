package edu.school21.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.service.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class TestApplicationConfig {
    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Bean
    public DataSource jdbcDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        driverManagerDataSource.setDriverClassName(driverClassName);
        return driverManagerDataSource;
    }

    @Bean
    public DataSource jdbcTemplateDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate (@Qualifier("jdbcTemplateDataSource") DataSource dataSource) {
        return new UsersRepositoryJdbcTemplateImpl(dataSource);
    }

    @Bean
    public UsersServiceImpl usersServiceImpl(UsersRepository<User> usersRepository) {
        return new UsersServiceImpl(usersRepository);
    }
}
