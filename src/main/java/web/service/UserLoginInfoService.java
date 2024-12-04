package web.service;

import web.model.UserLoginInfo;

public interface UserLoginInfoService {
    UserLoginInfo validateLogin(String email, String password);

    void addUserLoginInfo(UserLoginInfo userLoginInfo);

    UserLoginInfo findByUserId(Long userId);

    void updateUserLoginInfo(UserLoginInfo userLoginInfo);
}

