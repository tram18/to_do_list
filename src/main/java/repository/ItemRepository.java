package repository;

import entity.TaskItem;
import entity.TaskList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TaskItem> viewAllItems() {
        return entityManager.createNamedQuery("task_items.listAllTaskItem", TaskItem.class).getResultList();
    }

    public void addItems(int taskListId, TaskItem taskItem) {
        TaskList taskList = entityManager.find(TaskList.class, taskListId);
        if (taskList == null) {
            throw new IllegalArgumentException("TaskList not found");
        }
        taskItem.setTaskList(taskList);
        entityManager.persist(taskItem);

    }

    public void updateTaskCompletion(int itemId, boolean isCompleted) {
        TaskItem completedItem = entityManager.find(TaskItem.class, itemId);
        if (completedItem != null) {
            completedItem.setCompleted(isCompleted);
            entityManager.merge(completedItem);
        }
    }


}
