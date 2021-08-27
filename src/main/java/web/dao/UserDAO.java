package web.dao;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import java.util.List;
public interface UserDAO {
    public List<User> getAllUsers();
    public void saveUser(User user);
    public User getUserById(long id);
    public void updateUser(long id, User user);
    public void deleteUser(long id);
    public User getUserByLogin(String nickname);
    public void removeAll();
}
