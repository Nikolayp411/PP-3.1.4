package web.service;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.model.User;
import web.model.UserLoginInfo;
import web.repository.UserLoginInfoRepository;

import java.util.List;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserLoginInfoRepository userLoginInfoRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserLoginInfo loginInfo = userLoginInfoRepository.findByEmail(email);
        if (loginInfo == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        User user = loginInfo.getUser ();

        SimpleGrantedAuthority authority = user.getRole() != null
                ? new SimpleGrantedAuthority(user.getRole().getName())
                : null;

        return new org.springframework.security.core.userdetails.User(
                loginInfo.getEmail(),
                loginInfo.getPassword(),
                authority != null ? List.of(authority) : List.of()
        );
    }
}
