package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.UserLoginInfo;
import web.repository.RoleRepository;
import web.repository.UserLoginInfoRepository;
import web.repository.UserRepository;
import web.model.User;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;

import java.util.Set;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserLoginInfoRepository userLoginInfoRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Role userRole = new Role("USER");
            Role adminRole = new Role("ADMIN");
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }

        Role userRole = roleRepository.findByName("USER");
        Role adminRole = roleRepository.findByName("ADMIN");

        User john = new User("John", "Doe", "john.doe@example.com", userRole);
        userRepository.save(john);
        userLoginInfoRepository.save(new UserLoginInfo(john, "john.doe@example.com", passwordEncoder.encode("password123")));

        User jane = new User("Jane", "Doe", "jane.doe@example.com", userRole);
        userRepository.save(jane);
        userLoginInfoRepository.save(new UserLoginInfo(jane, "jane.doe@example.com", passwordEncoder.encode("password123")));

        User mike = new User("Mike", "Smith", "mike.smith@example.com", userRole);
        userRepository.save(mike);
        userLoginInfoRepository.save(new UserLoginInfo(mike, "mike.smith@example.com", passwordEncoder.encode("password123")));

        User sara = new User("Sara", "Johnson", "sara.johnson@example.com", userRole);
        userRepository.save(sara);
        userLoginInfoRepository.save(new UserLoginInfo(sara, "sara.johnson@example.com", passwordEncoder.encode("password123")));

        User tom = new User("Tom", "Brown", "tom.brown@example.com", adminRole);
        userRepository.save(tom);
        userLoginInfoRepository.save(new UserLoginInfo(tom, "tom.brown@example.com", passwordEncoder.encode("adminpass")));

        User testUser  = new User("test", "user", "user@user", userRole);
        userRepository.save(testUser );
        userLoginInfoRepository.save(new UserLoginInfo(testUser , "user@user", passwordEncoder.encode("user")));

        User adminUser  = new User("admin", "admin", "admin@admin", adminRole);
        userRepository.save(adminUser );
        userLoginInfoRepository.save(new UserLoginInfo(adminUser , "admin@admin", passwordEncoder.encode("admin")));
    }

    @PreDestroy
    @Transactional
    public void cleanup() {
        userLoginInfoRepository.deleteAll();
        userRepository.deleteAll();
    }
}
