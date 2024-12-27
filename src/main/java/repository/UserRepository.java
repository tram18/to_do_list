package repository;

import entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public List<User> listAllUsers() {
        return entityManager.createNamedQuery("users.listAllUsers", User.class).getResultList();
    }

    public User findUserById(int id) {
        try {
            return entityManager.createNamedQuery("users.findUserById", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addUser(User user) {
        try {
            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
            return true;
        }
        return false;
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

}
