package web.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.model.UserLoginInfo;

public interface UserLoginInfoRepository extends JpaRepository<UserLoginInfo, Long> {
    UserLoginInfo findByEmail(String email);
    UserLoginInfo findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE user_login_info AUTO_INCREMENT = 1;", nativeQuery = true)
    void IncrementNullification();
}
