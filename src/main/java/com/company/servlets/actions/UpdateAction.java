package com.company.servlets.actions;

import com.company.model.User;
import com.company.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateAction implements Action {

    private UserService userService;

    public UpdateAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        User user = new User(id, name);
        userService.update(user);
        response.sendRedirect("/users");
    }
}
