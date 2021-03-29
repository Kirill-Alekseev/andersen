package com.company.servlets;

import com.company.dao.ConnectionProvider;
import com.company.dao.DefaultConnectionProvider;
import com.company.dao.SshDatabaseConnectionProvider;
import com.company.service.SqlRunner;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TablesServlet", value = "/tables/*")
public class TablesServlet extends HttpServlet {

    private ConnectionProvider connectionProvider;
    private SqlRunner sqlRunner;

    @Override
    public void init() {
        connectionProvider = new DefaultConnectionProvider();
        sqlRunner = new SqlRunner(connectionProvider);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        sqlRunner.runSql("refresh_tables.sql");
        resp.sendRedirect("/users");
    }
}
