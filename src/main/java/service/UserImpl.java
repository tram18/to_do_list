package service;

import entity.User;
import repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserImpl implements UserService {

    @EJB
    UserRepository userRepository;

    @Override
    public List<User> listAllUsers() {
        return userRepository.listAllUsers();
    }

    @Override
    public User findUserWithId(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public boolean addUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
}
