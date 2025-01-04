package service;

import entity.TaskList;
import repository.TaskListRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TaskListServiceImpl implements TaskListService {

    @EJB
    TaskListRepository taskListRepository;

    @Override
    public List<TaskList> getAllTaskList() {
        return taskListRepository.getAllTaskList();
    }

    @Override
    public TaskList findTaskListById(int listId) {
        return taskListRepository.findTaskListById(listId);
    }

    @Override
    public void addTaskList(String taskName, int userId) {
        TaskList taskList = new TaskList();
        taskList.setListName(taskName);
        taskListRepository.addTask(taskList, userId);
    }

    @Override
    public void updateTaskList(TaskList taskList) {
        if(taskList != null) {
            taskListRepository.updateTaskList(taskList);
        }

    }

    @Override
    public List<TaskList> findAllTaskListsByUserId(int userId) {
        return taskListRepository.findAllTaskListsByUserId(userId);
    }

    @Override
    public boolean deleteList(int listId) {
        TaskList taskList = findTaskListById(listId);
        if (taskList != null) {
            taskListRepository.deleteTaskList(listId);
            return true;
        }
        return false;
    }


}
