package edu.school21.service.services;

import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService {
    private final UsersRepository<User> usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplateImpl") UsersRepository<User> usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        Optional<User> existingUser = usersRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            System.out.println("User with email " + email + " already exists");
            System.exit(0);
        }
        String tempPassword = UUID.randomUUID().toString();
        User newUser = new User(null, email, tempPassword);
        usersRepository.save(newUser);
        return tempPassword;
    }
}
