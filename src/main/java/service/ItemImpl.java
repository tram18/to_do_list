package service;

import entity.TaskItem;
import repository.ItemRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ItemImpl implements ItemService {

    @EJB
    private ItemRepository itemRepository;

    @Override
    public List<TaskItem> getAllItems() {
        return itemRepository.viewAllItems();
    }

    @Override
    public void addItems(String items, int taskListId) {
        TaskItem newItem = new TaskItem();
        newItem.setTaskName(items);
        itemRepository.addItems(taskListId, newItem);
    }

    @Override
    public void updateTaskCompletion(int itemId, boolean isCompleted) {
        itemRepository.updateTaskCompletion(itemId, isCompleted);
    }
}
