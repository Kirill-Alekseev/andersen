package com.company.servlets;

import com.company.dao.ConnectionProvider;
import com.company.dao.DefaultConnectionProvider;
import com.company.dao.UserDao;
import com.company.dao.UserDaoJdbc;
import com.company.model.User;
import com.company.service.UserService;
import com.company.service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "UserControllerServlet", value = "/users/*")
public class UserControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService;

    public void init() {
        ConnectionProvider connectionProvider = new DefaultConnectionProvider();
        UserDao userDao = new UserDaoJdbc(connectionProvider);
        this.userService = new UserServiceImpl(userDao);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();
        switch (action) {
            case "/users/new":
                showNewForm(req, resp);
                break;
            case "/users/insert":
                insertUser(req, resp);
                break;
            case "/users/delete":
                deleteBook(req, resp);
                break;
            case "/users/edit":
                showEditForm(req, resp);
                break;
            case "/users/update":
                updateUser(req, resp);
                break;
            default:
                listUsers(req, resp);
                break;
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Collection<User> users = userService.getAll();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/users/UsersList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/views/users/UserForm.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.getById(id);
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/users/UserForm.jsp");
        dispatcher.forward(request, response);

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        User user = new User(name);
        userService.create(user);
        response.sendRedirect("/users");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        User user = new User(name);
        user.setId(id);
        userService.update(user);
        response.sendRedirect("/users");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteById(id);
        response.sendRedirect("/users");
    }
}
