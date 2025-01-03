package repository;

import entity.TaskItem;
import entity.TaskList;
import entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TaskListRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TaskList> getAllTaskList() {
        return entityManager.createNamedQuery("task_lists.listAllTaskList", TaskList.class).getResultList();
    }

    public TaskList findTaskListById(int id) {
        return entityManager.find(TaskList.class, id);
    }

    public void addTask(TaskList taskList, int userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " not found.");
        }
        taskList.setUser(user);
        entityManager.persist(taskList);
    }

    public void updateTaskList(TaskList taskList, int userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " not found.");
        }
        taskList.setUser(user);
        entityManager.merge(taskList);
    }

    public boolean deleteTaskList(int id) {
        TaskList taskList = findTaskListById(id);
        if (taskList != null) {
            entityManager.remove(taskList); // Assuming JPA is used
            return true;
        }
        return false;
    }

    public List<TaskList> findAllTaskListsByUserId(int userId) {
        return entityManager.createNamedQuery("task_lists.findByUser", TaskList.class)
                .setParameter("userId", userId)
                .getResultList();
    }



}
