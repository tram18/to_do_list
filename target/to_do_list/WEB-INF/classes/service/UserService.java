package service;

import entity.User;

import java.util.List;

public interface UserService {

    List<User> listAllUsers();
    User findUserWithId(int id);
    boolean addUser(User user);
    boolean deleteUser(int id);
    void updateUser(User user);

}
