package AddPriceSalesreps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddPriceSalesreps {
    public static void main(String[] argv) throws SQLException {

        Statement stmt = null;
        try {
            // Open a connection
            JDBC.connect();
            // Execute a query
            stmt = JDBC.connection.createStatement();

            String name;
            String lastname;
            String price;

            BufferedReader enterPass = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter price of User Card: ");
            price = enterPass.readLine();

            BufferedReader enterUserName = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter First Name User Card: ");
            name = enterUserName.readLine();

            BufferedReader enterLastName = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Last Name User Card: ");
            lastname = enterLastName.readLine();

            //  String query = "SELECT * FROM authors ORDER BY lastName , firstName ";
            String sql = "INSERT INTO countsals(price, fname, lname) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, price);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastname);

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Added %d object\n", rows);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Alphabatized all rows by price, first and last name:");

        ResultSet rs1 = stmt.executeQuery("SELECT * FROM countsals");


        while (rs1.next()) {
            String price = rs1.getString("price");
            String firstName = rs1.getString("fname");
            String lastName = rs1.getString("lname");
            System.out.println("\t" + price + "\t|\t" + firstName + "\t|\t" + lastName);
        }
//finally block used to close resources
        JDBC.close();
    }
}
