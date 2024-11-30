package web.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE users", nativeQuery = true)
    void truncate();
}
