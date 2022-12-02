package org.example.ers.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFactory {
    private static PropertiesFactory propertiesFactory;
    private final Properties properties = new Properties();


    private PropertiesFactory() {
        try {
            properties.load(new FileReader("resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesFactory getInstance() {
        if (propertiesFactory == null) {
            propertiesFactory = new PropertiesFactory();
        }
        return propertiesFactory;
    }

    public Properties getProperties() {
        return properties;
    }
}
