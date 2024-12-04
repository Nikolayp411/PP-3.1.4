package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
                    createUser ("John", "Doe", "john.doe@example.com", "password123", userRole),
                    createUser ("Jane", "Doe", "jane.doe@example.com", "password123", userRole),
                    createUser ("Mike", "Smith", "mike.smith@example.com", "password123", userRole),
                    createUser ("Sara", "Johnson", "sara.johnson@example.com", "password123", userRole),
                    createUser ("Tom", "Brown", "tom.brown@example.com", "adminpass", adminRole),
                    createUser ("test", "user", "user@user", "user", userRole),
                    createUser ("admin", "admin", "admin@admin", "admin", adminRole)
            );

            userRepository.saveAll(users);
        } else {
            System.out.println("Roles not found! Users will not be created.");
        }
    }

    private User createUser (String firstName, String lastName, String email, String password, Role role) {
        User user = new User(firstName, lastName, email, List.of(role));
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }
}
