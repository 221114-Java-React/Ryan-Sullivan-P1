package org.example.ers.utilities;

import java.io.FileNotFoundException;
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

    private final Properties properties = new Properties();

    private ConnectionFactory() {
        try {
            properties.load(new FileReader("resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    public Connection getConnection() {
        System.out.println("getting connection");
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
