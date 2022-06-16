package terminal;

import terminal.JDBCDriver.JDBC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Assembly {
    public static void ShowAssemblyClass() throws IOException {
        //bind with DB
        Statement stmt = null;
        try {
            // Open a connection
            JDBC.connect();
            // Execute a query
            stmt = JDBC.connection.createStatement();

            //logic without BD
      /*  System.out.println("Enter Your name: ");
        BufferedReader inputname = new BufferedReader(new InputStreamReader(System.in));
        String inname = inputname.readLine();
        System.out.println("Your name is " + inname);*/
            for (; ; ) {
                BufferedReader enterSelectPosition = new BufferedReader(new InputStreamReader(System.in));
                String selectPosition;
                ResultSet rs1 = stmt.executeQuery("SELECT password FROM password");
                String pass = null;
                while (rs1.next()) {
                    pass = rs1.getString("password");
                    //   System.out.println("\t" + pass);
                }

                System.out.println("Enter password Your card:");
                Scanner inputpassword = new Scanner(System.in);
                String enter_password_card = inputpassword.next();
                if (/*inname.equals("Vitali") &*/ enter_password_card.equals(pass)) {
                    // System.out.println("This card belong " + inname);
                    Activity activity = new Activity(enter_password_card);
                    do {
                        activity.showMenu();
                        selectPosition = enterSelectPosition.readLine();
                        activity.caseMenu(selectPosition);
                        if (selectPosition == "q") break;
                    } while (activity.Valid(selectPosition));
                    if (selectPosition == "q") break;
                } else {
                    System.out.println("Error, is not your card. Run application again");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBC.close();
    }
}
