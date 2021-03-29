package com.company.service;

import com.company.dao.ConnectionProvider;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public class SqlRunner {

    private ConnectionProvider connectionProvider;

    public SqlRunner(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void runSql(String sqlScript) {
        try (Connection connection = connectionProvider.getConnection()) {
            ScriptRunner runner = new ScriptRunner(connection);
            Reader reader = Resources.getResourceAsReader(sqlScript);
            runner.runScript(reader);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
