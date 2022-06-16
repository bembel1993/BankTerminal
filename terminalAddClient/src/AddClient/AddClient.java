package AddClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddClient {
    public static void main(String[] argv) throws SQLException {

        Statement stmt = null;
        try {
            // Open a connection
            JDBC.connect();
            // Execute a query
            stmt = JDBC.connection.createStatement();

            String name;
            String lastname;
            String password;

            BufferedReader enterUserName = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter First Name User Card: ");
            name = enterUserName.readLine();

            BufferedReader enterLastName = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Last Name User Card: ");
            lastname = enterLastName.readLine();

            BufferedReader enterPass = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter password of User Card: ");
            password = enterPass.readLine();

            //  String query = "SELECT * FROM authors ORDER BY lastName , firstName ";
            String sql = "INSERT INTO password(name, lastname, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, password);

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Added %d object\n", rows);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Alphabatized all rows by first and last name:");

        ResultSet rs1 = stmt.executeQuery("SELECT * FROM password");


        while (rs1.next()) {
            String firstName = rs1.getString("name");
            String lastName = rs1.getString("lastname");
            String pass = rs1.getString("password");
            System.out.println("\t" + firstName + "\t|\t" + lastName + "\t|\t" + pass);
        }
//finally block used to close resources
        JDBC.close();
    }
}
    /*    System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            JDBC.connect();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return;
        }


        JDBC.close();
    }*/
