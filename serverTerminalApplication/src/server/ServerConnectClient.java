package server;

import JDBC.JDBC;

import java.io.*;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ServerConnectClient {
    public Socket server_socket;
    public BufferedReader read_password_card_fromClient;
    public BufferedReader read_num_Switch_Case;
    public BufferedReader read_QTY_Money_from_Client;
    public PrintWriter QTY_Money_for_Client_send;
    public PrintWriter send_toClient;
    public String getSwitchCase;
    public PrintWriter send_Client_his_Money;
    public String new_read_QTY_Money_from_Client;
    public static int money = 2000;
    Statement stmt = null;
    ResultSet rs1;

    //this Object get parameter type Socket Object socket (addr, port, localport)
    public ServerConnectClient(Socket socket) throws IOException {
        this.server_socket = socket;
    }

    //this method send from Server input stream to Client
    public void sendtoClient() throws IOException, SQLException {
        rs1 = stmt.executeQuery("SELECT * FROM countsals");
        String price = null;
        String fname;
        String lname;
        while (rs1.next()) {
            price = rs1.getString("price");
            fname = rs1.getString("fname");
            lname = rs1.getString("lname");
            System.out.println("\t"+price+"\t\t" + fname + "\t\t" + lname);
        }
        send_toClient = new PrintWriter(server_socket.getOutputStream(), true);
        send_toClient.println("~ Server - You have money of your account: " + price + "$");
        System.out.println("Answer to Client send");
    }

    //this method get and read from Client output stream
    public void readfromClient() throws IOException {
        read_password_card_fromClient = new BufferedReader(new InputStreamReader(server_socket.getInputStream()));
        String getNumCard = read_password_card_fromClient.readLine();

        System.out.println("~Client - Number card of user: " + getNumCard);
        if (getNumCard.equals("q")) {
            System.out.println("Client is out");
        }
    }

    public void readfromClientSwitchCase() throws IOException {

//bind with DB
       // Statement stmt = null;
        try {
            // Open a connection
            JDBC.connect();
            // Execute a query
            stmt = JDBC.connection.createStatement();

            readfromClient();
            sendtoClient();
            read_num_Switch_Case = new BufferedReader(new InputStreamReader(server_socket.getInputStream()));
            getSwitchCase = read_num_Switch_Case.readLine();
            System.out.println("~Switch Case from Client: " + getSwitchCase);
            switch (getSwitchCase) {
                case "1":
                    rs1 = stmt.executeQuery("SELECT * FROM countsals");
                    String price = null;
                    String fname;
                    String lname;
                    while (rs1.next()) {
                        price = rs1.getString("price");
                        fname = rs1.getString("fname");
                        lname = rs1.getString("lname");
                        System.out.println("\t"+price+"\t\t" + fname + "\t\t" + lname);
                    }
                    System.out.println("Case 1 from Client. Do send to Client check of his money: " + price);
                    break;
                case "2":
                    System.out.println("Case 2 from Client. Give out money of this Client");
                    System.out.println("Case are choose");
                    read_Client_toCalculation();
                    sendtoClientwithCalculation();
                    break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBC.close();
    }

    /*public void Manipulation_with_getSwitchCase(String getSwitch) {
        String manipulation_getSwitch = getSwitch;
        switch (manipulation_getSwitch){
            case "1":
                System.out.println("Case 1 from Client. Do send to Client his money" + money);
                break;
            case "2":
                System.out.println("Case 2 from Client. Do make request for pay");
                break;
        }
    }*/

    public void read_Client_toCalculation() throws IOException {
        read_QTY_Money_from_Client = new BufferedReader(new InputStreamReader(server_socket.getInputStream()));
        new_read_QTY_Money_from_Client = read_QTY_Money_from_Client.readLine();
        System.out.println("QTY for withdraw: " + new_read_QTY_Money_from_Client);
    }

    public void sendtoClientwithCalculation() throws IOException, SQLException {
        rs1 = stmt.executeQuery("SELECT * FROM countsals");
        String price = null;
        while (rs1.next()) {
            price = rs1.getString("price");
           // fname = rs1.getString("fname");
           // lname = rs1.getString("lname");
            System.out.println("\t"+price+"\t");
        }
        int priceFromDB = Integer.parseInt(price);
        int moneyWithdraw = Integer.parseInt(new_read_QTY_Money_from_Client);
        int get_Money_minus_Money_of_need_to_Client = priceFromDB - moneyWithdraw;
        priceFromDB = get_Money_minus_Money_of_need_to_Client;

        String sql = "INSERT INTO countsals(price) VALUES (?)";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);

        String sendMoneyMinusWithdraw = Integer.toString(get_Money_minus_Money_of_need_to_Client);

        preparedStatement.setString(1, sendMoneyMinusWithdraw);
        int rows = preparedStatement.executeUpdate();

        send_Client_his_Money = new PrintWriter(server_socket.getOutputStream(), true);
        send_Client_his_Money.println("~ Server - You withdraw money: " + new_read_QTY_Money_from_Client);
        System.out.println("~ The rest of money: " + sendMoneyMinusWithdraw + " $");
        System.out.println("Answer to Client send");
    }
}
