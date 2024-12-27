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

    @EJB
    private TaskListService taskListService;

    @EJB
    private UserService userService;

    @EJB
    private ItemService itemService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-------------------DoGet");
        showTaskList(req);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addList(req);
        addItem(req);
        resp.sendRedirect(req.getContextPath() + "/");
//        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    public void showTaskList(HttpServletRequest req) {
        List<TaskList> taskLists = new ArrayList<>();
        taskLists = taskListService.findAllTaskListsByUserId(2);
        req.setAttribute("taskLists", taskLists);

    }

    public void addList(HttpServletRequest req) {
        String listName = req.getParameter("listName");
        try {
            taskListService.addTaskList(listName, 2);
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


}