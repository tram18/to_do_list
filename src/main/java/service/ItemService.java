package service;

import entity.TaskItem;

import java.util.List;

public interface ItemService {
    List<TaskItem> getAllItems();
    void addItems(String items, int taskListId);
    void updateTaskCompletion(int itemId, boolean isCompleted);
    void deleteItem(int itemId);
}
