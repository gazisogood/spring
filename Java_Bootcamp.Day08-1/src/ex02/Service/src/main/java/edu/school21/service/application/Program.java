package edu.school21.service.application;

import edu.school21.service.config.ApplicationConfig;
import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.services.UsersService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersService usersService = context.getBean("usersServiceImpl",UsersService.class);
        usersService.signUp("arboriob@student.21-school.ru");
        UsersRepository<User> user = context.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);
        Optional<User> res = user.findByEmail("arboriob@student.21-school.ru");
        res.ifPresent(System.out::println);
    }
}
