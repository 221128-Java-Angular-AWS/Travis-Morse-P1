package com.revature.persistence;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {

    private static Connection connection;

    private ConnectionManager() {
        // Constructor deliberately left blank
    }

    public static Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    private static void connect() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("connection.properties");
            Properties props = new Properties();
            props.load(input);
            StringBuilder builder = new StringBuilder();
            builder.append("jdbc:postgresql://");
            builder.append(props.getProperty("host"));
            builder.append(":");
            builder.append(props.getProperty("port"));
            builder.append("/");
            builder.append(props.getProperty("dbname"));
            builder.append("?user=");
            builder.append(props.getProperty("username"));
            builder.append("&password=");
            builder.append(props.getProperty("password"));

            Class.forName(props.getProperty("driver")); // Weird error prevention

            connection = DriverManager.getConnection(builder.toString());

        }
        catch (Exception e) {
            //TODO: improve exception handling
            e.printStackTrace();
        }
    }
}
