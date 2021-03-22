package com.company.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.company.io.PropertiesReader;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "application.properties";

    private static DatabaseConnection instance;
    private PropertiesReader propertiesReader = new PropertiesReader();

    private String url;
    private String username;
    private String password;
    private String driverName;

    private DatabaseConnection() {
        try {
            Properties properties = propertiesReader.readPropertiesFile(PROPERTIES_FILE);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            driverName = properties.getProperty("driverName");
            Class.forName(driverName);
        } catch (Exception ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
