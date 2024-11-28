package web.Initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.UserDao;
import web.model.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
public class DataInitializer {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        userDao.add(new User("John", "Doe", "john.doe@example.com"));
        userDao.add(new User("Jane", "Doe", "jane.doe@example.com"));
        userDao.add(new User("Mike", "Smith", "mike.smith@example.com"));
        userDao.add(new User("Sara", "Johnson", "sara.johnson@example.com"));
        userDao.add(new User("Tom", "Brown", "tom.brown@example.com"));
    }

    @PreDestroy
    @Transactional
    public void cleanup() {
        userDao.truncateUsers();
    }
}
