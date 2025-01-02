package service;

import entity.TaskList;

import java.util.List;

public interface TaskListService {

    List<TaskList> getAllTaskList();
    TaskList findTaskListById(int listId);
    void addTaskList(String taskName, int userId);
    void updateTaskList(String taskName, int userId);
    List<TaskList> findAllTaskListsByUserId(int userId);
    void deleteList(int listId);
}
