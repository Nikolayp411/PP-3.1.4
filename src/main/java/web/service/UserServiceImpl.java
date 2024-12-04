package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.repository.UserRepository;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser (User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser (User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser (Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
