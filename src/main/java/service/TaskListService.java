package service;

import entity.TaskList;

import java.util.List;

public interface TaskListService {

    List<TaskList> getAllTaskList();
    TaskList getTaskListsByUserId(int userId);
    void addTaskList(String taskName, int userId);
    void updateTaskList(String taskName, int userId);
    void deteleTaskList(int id);
    List<TaskList> findAllTaskListsByUserId(int userId);
    void deleteList(int listId);
}
