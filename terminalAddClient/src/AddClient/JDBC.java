package AddClient;

import java.sql.*;

public class JDBC {

    public static Connection connection = null;

    public static void connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            throw new SQLException();
        }
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/forterminal?useUnicode=true&serverTimezone=UTC",
                "root", "root");
        if(connection == null) {
            throw new SQLException();
        } else {
            System.out.println("Successfully connected");
        }
    }

    public static void close() {
        try {
            if(connection != null) {
                connection.close();
                System.out.println("Closing connection");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection!");
        }
    }

}