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
    public TaskList getTaskListsByUserId(int id) {
        return taskListRepository.findTaskListById(id);
    }

    @Override
    public void addTaskList(String taskName, int userId) {
        TaskList taskList = new TaskList();
        taskList.setListName(taskName);
        taskListRepository.addTask(taskList, userId);
    }

    @Override
    public void updateTaskList(String taskName, int userId) {
    //todo
    }

    @Override
    public void deteleTaskList(int id) {
        taskListRepository.deleteTaskList(id);
    }

    @Override
    public List<TaskList> findAllTaskListsByUserId(int userId) {
        return taskListRepository.findAllTaskListsByUserId(userId);
    }

    @Override
    public void deleteList(int listId) {
        taskListRepository.deleteTaskList(listId);
    }


}
