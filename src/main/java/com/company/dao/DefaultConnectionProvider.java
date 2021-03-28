package com.company.dao;

import com.company.io.PropertiesReader;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DefaultConnectionProvider implements ConnectionProvider {

    private static final String PROPERTIES_FILE = "application.properties";

    private BasicDataSource dataSource;

    private String URL;
    private String USERNAME;
    private String PASSWORD;
    private String DRIVER_NAME;

    public DefaultConnectionProvider() {
        try {
            PropertiesReader propertiesReader = new PropertiesReader();
            Properties properties = propertiesReader.readPropertiesFile(PROPERTIES_FILE);
            this.URL = properties.getProperty("url");
            this.USERNAME = properties.getProperty("username");
            this.PASSWORD = properties.getProperty("password");
            this.DRIVER_NAME = properties.getProperty("driverName");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DefaultConnectionProvider(String url, String username, String password, String driverName) {
        this.URL = url;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.DRIVER_NAME = driverName;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            configureDataSource();
        }
        return dataSource.getConnection();
    }

    public void configureDataSource() {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
        dataSource.setDriverClassName(DRIVER_NAME);
    }
}
