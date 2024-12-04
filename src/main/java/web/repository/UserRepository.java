package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //в оригинале это findbyusername но у нас юзернейм це емайл
    User findByEmail(String username);
}
