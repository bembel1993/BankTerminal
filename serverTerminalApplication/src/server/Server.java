package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT_one = 1991;
    // public static final int PORT_two = 1992;

    public static void main(String[] args) throws IOException {
        ServerSocket server_one = new ServerSocket(PORT_one);
        // ServerSocket server_two = new ServerSocket(PORT_two);
        System.out.println("Server one is READY!");
        try {
            System.out.println(server_one);//creat server with port 1993
            while (true) {
                Socket socket = server_one.accept();//created server get socket connected
                System.out.println("Connect a client with server access successfully ");
                System.out.println(socket); //connect server with socket
                try {
                    ServerConnectClient scc = new ServerConnectClient(socket); //creat Object ServerConnectClient
                    scc.readfromClientSwitchCase();
                    //scc.read_Client_toCalculation();
                    //scc.sendtoClientwithCalculation();
                } catch (IOException e) {
                    System.out.println("///Socket - close///");
                    System.out.println("Client " + socket + "is out");
                    socket.close();
                }
            }
        } finally {
            server_one.close();
        }
    }
}
