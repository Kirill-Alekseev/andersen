package com.company.servlets.actions;

import com.company.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListAction implements Action {

    private UserService userService;

    public ListAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", userService.getAll());
        request.getRequestDispatcher("/views/users/UsersList.jsp").forward(request, response);
    }
}
