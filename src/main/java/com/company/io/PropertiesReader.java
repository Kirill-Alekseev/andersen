package com.company.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final String FILE_NAME = "application.properties";

    public String readProperty(String key) {
        String result = null;
        try {
            result = readPropertiesFile(FILE_NAME).getProperty(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Properties readPropertiesFile(String file) throws FileNotFoundException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file)) {
            properties.load(inputStream);
        } catch (IOException | NullPointerException e) {
            throw new FileNotFoundException(file);
        }
        return properties;
    }
}
