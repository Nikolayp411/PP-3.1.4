package web.service;

import web.model.User;

public interface UserProfileService {
    User findByEmail(String email);
    void updateUser (User user);
}
