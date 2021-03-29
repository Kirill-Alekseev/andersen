package com.company.servlets.actions;

import com.company.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditAction implements Action {

    private UserService userService;

    public EditAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("user", userService.getById(id));
        request.getRequestDispatcher("/views/users/UserForm.jsp").forward(request, response);
    }

}
