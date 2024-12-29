package controller;

import entity.TaskList;
import service.ItemService;
import service.TaskListService;
import service.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TodoServlet", urlPatterns = "")
public class TodoServlet extends HttpServlet {
    private static final long serialVersionUID = -8841769146082323925L;
    private static final int userId = 2;

    @EJB
    private TaskListService taskListService;

    @EJB
    private UserService userService;

    @EJB
    private ItemService itemService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-------------------------------------------------------------------DoGet");
        showTaskList(req); // why 3 times appear if sth went wrong?

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-------------------------------------------------------------------do post");
        String action = req.getParameter("action");

        try {
            if ("addList".equals(action)) {
                addList(req);
            } else if ("addItem".equals(action)) {
                addItem(req);
            } else if ("addCompletedItem".equals(action)) {
                addCompletedItem(req);
            } else if ("deleteItem".equals(action)) {
                deleteItem(req);
            } else {
                throw new IllegalArgumentException("Unknown action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "An error occurred while processing your request.");
        }

        resp.sendRedirect(req.getContextPath() + "/");
    }

    public void showTaskList(HttpServletRequest req) {
        List<TaskList> taskLists = new ArrayList<>();
        taskLists = taskListService.findAllTaskListsByUserId(userId);
        req.setAttribute("taskLists", taskLists);

    }

    public void addList(HttpServletRequest req) {
        String listName = req.getParameter("listName");
        try {
            taskListService.addTaskList(listName, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItem(HttpServletRequest req) {
        int taskListId = Integer.parseInt(req.getParameter("taskListId"));
        String itemName = req.getParameter("itemName");

        try {
            itemService.addItems(itemName, taskListId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCompletedItem(HttpServletRequest req) {
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        String completed = req.getParameter("completed");
        boolean taskCompleted = "true".equals(completed);

        itemService.updateTaskCompletion(taskId, taskCompleted);
    }

    public void deleteItem(HttpServletRequest req) {
        String item = req.getParameter("itemId");
        int itemId = Integer.parseInt((req.getParameter("itemId")));
        try {
            itemService.deleteItem(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}