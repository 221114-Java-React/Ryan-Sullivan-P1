package org.example.ers.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static ConnectionFactory connectionFactory;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Properties properties;

    private ConnectionFactory() {
        properties = PropertiesFactory.getInstance().getProperties();
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getProperty("url"),
                                                                properties.getProperty("username"),
                                                                properties.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not establish connection with DB!");
            System.exit(1);
        }
        return connection;
    }
}
