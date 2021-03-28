package com.company.servlets.actions;

import com.company.model.User;
import com.company.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InsertAction implements Action {

    private UserService userService;

    public InsertAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        User user = new User(name);
        userService.create(user);
        response.sendRedirect("/users");
    }
}
