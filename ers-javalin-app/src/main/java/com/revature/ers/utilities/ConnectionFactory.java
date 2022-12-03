package com.revature.ers.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static ConnectionFactory connectionFactory;
    private final static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

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
            logger.info(e.getMessage());
        }
        return connection;
    }
}
