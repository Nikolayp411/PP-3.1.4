package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import web.model.User;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void init() {
        initializeRoles();
        initializeUsers();
    }

    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role userRole = new Role("USER");
            Role adminRole = new Role("ADMIN");
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }
    }

    private void initializeUsers() {
        Role userRole = roleRepository.findByName("USER");
        Role adminRole = roleRepository.findByName("ADMIN");

        if (userRole != null && adminRole != null) {
            List<User> users = Arrays.asList(
                    createUser ("John", "Doe", "john.doe@example.com", "password123", userRole, 30),
                    createUser ("Jane", "Doe", "jane.doe@example.com", "password123", userRole, 28),
                    createUser ("Mike", "Smith", "mike.smith@example.com", "password123", userRole, 35),
                    createUser ("Sara", "Johnson", "sara.johnson@example.com", "password123", userRole, 22),
                    createUser ("Tom", "Brown", "tom.brown@example.com", "adminpass", adminRole, 40),
                    createUser ("test", "user", "user@user", "user", userRole, 25),
                    createUser ("admin", "admin", "admin@admin", "admin", adminRole, 45)
            );

            userRepository.saveAll(users);
        } else {
            System.out.println("Roles not found! Users will not be created.");
        }
    }

    private User createUser (String firstName, String lastName, String email, String password, Role role, Integer age) {
        User user = new User(firstName, lastName, email, age, List.of(role));
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }
}
