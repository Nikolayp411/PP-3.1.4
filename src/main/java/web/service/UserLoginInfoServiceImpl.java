package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.model.UserLoginInfo;
import web.repository.UserLoginInfoRepository;

@Service
public class UserLoginInfoServiceImpl implements UserLoginInfoService {

    @Autowired
    private UserLoginInfoRepository userLoginInfoRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserLoginInfo validateLogin(String email, String password) {
        UserLoginInfo userLoginInfo = userLoginInfoRepository.findByEmail(email);
        if (userLoginInfo != null && passwordEncoder.matches(password, userLoginInfo.getPassword())) {
            return userLoginInfo;
        }
        return null;
    }

    @Override
    public void addUserLoginInfo(UserLoginInfo userLoginInfo) {
        userLoginInfoRepository.save(userLoginInfo);
    }

    @Override
    public void updateUserLoginInfo(UserLoginInfo userLoginInfo) {
        userLoginInfoRepository.save(userLoginInfo);
    }

    @Override
    public UserLoginInfo findByUserId(Long userId) {
        return userLoginInfoRepository.findByUserId(userId);
    }

}
