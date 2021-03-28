package com.company.servlets.actions;

import com.company.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAction implements Action {

    private UserService userService;

    public DeleteAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteById(id);
        response.sendRedirect("/users");
    }
}
