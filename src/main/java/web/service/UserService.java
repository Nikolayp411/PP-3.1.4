package web.service;

import web.model.User;
import java.util.List;

public interface UserService {
    List<User> listUsers();
    void addUser (User user);
    void updateUser (User user);
    void deleteUser (Long userId);
    User getUserById(Long userId);
    User findByEmail(String email);
}
