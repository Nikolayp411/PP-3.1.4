package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.repository.UserRepository;
import web.model.User;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        userRepository.save(new User("John", "Doe", "john.doe@example.com"));
        userRepository.save(new User("Jane", "Doe", "jane.doe@example.com"));
        userRepository.save(new User("Mike", "Smith", "mike.smith@example.com"));
        userRepository.save(new User("Sara", "Johnson", "sara.johnson@example.com"));
        userRepository.save(new User("Tom", "Brown", "tom.brown@example.com"));
    }

    @PreDestroy
    @Transactional
    public void cleanup() {
        userRepository.truncate();
    }
}
