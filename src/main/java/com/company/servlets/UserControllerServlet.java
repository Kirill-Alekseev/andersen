package com.company.servlets;

import com.company.dao.*;
import com.company.service.UserService;
import com.company.service.UserServiceImpl;
import com.company.servlets.actions.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "UserControllerServlet", value = "/users/*")
public class UserControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private HashMap<String, Action> actions = new HashMap<>();

    public void init() {
        ConnectionProvider connectionProvider = new SshDatabaseConnectionProvider();
        UserDao userDao = new UserDaoJdbc(connectionProvider);
        UserService userService = new UserServiceImpl(userDao);

        actions.put("/users/new", new NewAction());
        actions.put("/users/insert", new InsertAction(userService));
        actions.put("/users/delete", new DeleteAction(userService));
        actions.put("/users/edit", new EditAction(userService));
        actions.put("/users/update", new UpdateAction(userService));
        actions.put("/users", new ListAction(userService));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        Action action = actions.get(uri);

        action.execute(req, resp);
    }

}
