package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    EntityManager entityManager;

    public UserDAOImpl() {
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User").getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(long id, User user) {
        user.setId(id);
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserByNickName(String nickname) {
      Query query =  entityManager.createNativeQuery("SELECT nick_name FROM users WHERE nick_name = " + nickname, User.class);
      query.executeUpdate();
      System.out.println("Test name" + nickname);
      User user = (User) query.getResultList().get(0);
      return user ;
    }

    @Override
    public void removeAll() {
        List<User> allUsers = getAllUsers();
        allUsers.forEach(u -> deleteUser(u.getId()));
    }
}
