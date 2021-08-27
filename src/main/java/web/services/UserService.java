package web.services;

import web.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> getAllUsers();
    User getUserById(long id);
    public void updateUser(long id, User user);
    public void deleteUser(long id);
    public User getUserByLogin(String login);
    public void removeAll();
}
