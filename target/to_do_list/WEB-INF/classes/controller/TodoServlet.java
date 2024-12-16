package controller;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action = req.getParameter("addItem");
        List<String> listOfItems = new ArrayList<>();
        String item = req.getParameter("item");
        listOfItems.add(item);

        req.setAttribute("list", listOfItems);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

}