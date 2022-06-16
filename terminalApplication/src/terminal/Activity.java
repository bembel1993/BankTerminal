package terminal;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Activity extends Interface implements Runnable {
    public Socket socket;
    public BufferedWriter bw;
    public String password_card_input;
    public PrintWriter toServer;
    public PrintWriter numberCaseforServer;
    public String casenum;
    public BufferedReader fromServer;
    public BufferedReader enter_QTY_of_Money;
    public PrintWriter enter_money;
    public int Sum;

    public Activity(String password_card) {
        super(password_card);
        password_card_input = password_card;
    }

    @Override
    public void showMenu() {
        System.out.println("What do You want to do?");
        System.out.println("1. View money");
        System.out.println("2. Withdraw money");
        System.out.println("3. Remit payment");
        System.out.println("Exit - q");
    }

    @Override
    public boolean Valid(String ch) {
        if (ch == "1" || ch == "2" || ch == "3" & ch != "q") {
            return true;
        } else {
            return false;
        }
    }

    // @Override
    public void caseMenu(String enter) throws IOException {
        casenum = enter;
        switch (enter) {
            case "1":
                socket = new Socket("127.0.0.1", 1991);
                System.out.println("Connecting with Bank is set");
                System.out.println("1.Your money");
                System.out.println("------------------");
                System.out.println(socket);
                numberCaseforServer = new PrintWriter(socket.getOutputStream(), true);
                toServer = new PrintWriter(socket.getOutputStream(), true);
                fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                run();
                readfromServer();
                sendserverswitchcase();
                break;
            case "2":
                socket = new Socket("127.0.0.1", 1991);
                System.out.println("Connecting with Bank is set");
                System.out.println("2. Withdraw money");
                System.out.println("------------------");
                System.out.println(socket);
                numberCaseforServer = new PrintWriter(socket.getOutputStream(), true);
                toServer = new PrintWriter(socket.getOutputStream(), true);
                fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                enter_QTY_of_Money = new BufferedReader(new InputStreamReader(System.in));
                enter_money = new PrintWriter(socket.getOutputStream(), true);
                run();
                readfromServer();
                sendserverswitchcase();
                enter_how_much_Money_want_to_withdraw();
                try {
                    read_enter_how_much_Money_want_to_withdraw_from_Server();
                }catch (IOException ex){
                    System.out.println("Not answer from Server");
                }
                break;
            case "3":
                socket = new Socket("127.0.0.1", 1991);
                System.out.println("Connecting with Bank is set");
                System.out.println("3. Remit payment");
                System.out.println("------------------");
                System.out.println("Enter please service");
                System.out.println("1. Utilities");
                System.out.println("2. Mobile connection");
                switch (enter) {
                    case "1":
                        System.out.println("3. Remit payment");
                        System.out.println("1. Utilities");
                        System.out.println("Enter sum: ");
                        Scanner input1 = new Scanner(System.in);
                        Sum = input1.nextInt();
                        System.out.println("Introduced: " + Sum);
                        System.out.println("Service paid");
                    case "2":
                        System.out.println("3. Remit payment");
                        System.out.println("2. Mobile connection");
                        System.out.println("Enter sum: ");
                        Scanner input2 = new Scanner(System.in);
                        Sum = input2.nextInt();
                        System.out.println("Introduced: " + Sum);
                        System.out.println("Mobile paid");
                }
        }
    }

    @Override
    public void run() {
        String send_password_card_toServer;

        send_password_card_toServer = password_card_input;
        toServer.println(send_password_card_toServer);
        System.out.println("Request to Server send.");
        //System.out.println("Password: " + send_password_card_toServer);

    }

    public void sendserverswitchcase() {
        String number_case_for_Server;
        number_case_for_Server = casenum;
        numberCaseforServer.println(number_case_for_Server);
        System.out.println("Number for server: " + number_case_for_Server);
    }

    public void enter_how_much_Money_want_to_withdraw() throws IOException {
        String qty_of_Money;
        //String read_Answer_from_Server;

       //enter_QTY_of_Money = new BufferedReader(new InputStreamReader(System.in));
       //enter_money = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Enter money for withdraw");
        qty_of_Money = enter_QTY_of_Money.readLine();
        enter_money.println(qty_of_Money);
        System.out.println("Money for withdraw for server: " + qty_of_Money);

       // read_Answer_from_Server = fromServer.readLine();
       // System.out.println("You withdraw: " + read_Answer_from_Server);
    }

    public void read_enter_how_much_Money_want_to_withdraw_from_Server() throws IOException {
        String read_Answer_from_Server;

        read_Answer_from_Server = fromServer.readLine();
        System.out.println("You withdraw: " + read_Answer_from_Server);
    }

    public void readfromServer() throws IOException {
        String get_qty_money_fromServer;
        get_qty_money_fromServer = fromServer.readLine();
        System.out.println(get_qty_money_fromServer);
    }
}
