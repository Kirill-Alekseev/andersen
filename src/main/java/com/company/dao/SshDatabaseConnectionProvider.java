package com.company.dao;

import com.company.io.PropertiesReader;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class SshDatabaseConnectionProvider implements ConnectionProvider {

    private static final String PROPERTIES_FILE = "application.properties";

    private static Session sshSession;
    private BasicDataSource dataSource;

    private String URL, DB_USERNAME, DB_PASSWORD, DRIVER_NAME, DB_HOST;
    private String SSH_USERNAME, SSH_PASSWORD, SSH_HOST;
    private int LOCAL_PORT, DB_PORT, SSH_PORT;

    public SshDatabaseConnectionProvider() {
        try {
            PropertiesReader propertiesReader = new PropertiesReader();
            Properties properties = propertiesReader.readPropertiesFile(PROPERTIES_FILE);
            URL = properties.getProperty("database_url");
            DB_USERNAME = properties.getProperty("username");
            DB_PASSWORD = properties.getProperty("password");
            DRIVER_NAME = properties.getProperty("driverName");
            SSH_USERNAME = propertiesReader.readProperty("ssh_username");
            SSH_PASSWORD = propertiesReader.readProperty("ssh_password");
            SSH_HOST = propertiesReader.readProperty("ssh_host");
            SSH_PORT = Integer.parseInt(propertiesReader.readProperty("ssh_port"));
            LOCAL_PORT = Integer.parseInt(properties.getProperty("local_port"));
            DB_PORT = Integer.parseInt(properties.getProperty("database_port"));
            DB_HOST = properties.getProperty("database_host");
        } catch (Exception ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            configureDataSource();
        }
        return dataSource.getConnection();
    }

    private void configureDataSource() {
        try {
            openSession();
            dataSource = new BasicDataSource();
            dataSource.setUrl(URL);
            dataSource.setUsername(DB_USERNAME);
            dataSource.setPassword(DB_PASSWORD);
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxOpenPreparedStatements(100);
            dataSource.setDriverClassName(DRIVER_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSession() throws JSchException {
        if (sshSession == null || !sshSession.isConnected()) {
            sshSession = new JSch().getSession(SSH_USERNAME, SSH_HOST, SSH_PORT);
            sshSession.setPassword(SSH_PASSWORD);
            sshSession.setConfig("StrictHostKeyChecking", "no");
            sshSession.connect();
            sshSession.setPortForwardingL(LOCAL_PORT, DB_HOST, DB_PORT);
        }
    }

    @PreDestroy
    public static void closeSession() {
        if (sshSession != null) {
            sshSession.disconnect();
        }
    }

}
