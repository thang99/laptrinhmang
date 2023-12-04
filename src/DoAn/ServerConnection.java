/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DoAn;

/**
 *
 * @author ACER
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
    private static ServerConnection instance;
    private Socket socket;

    private ServerConnection() {
        // Private constructor to enforce singleton pattern
        connectToServer();
    }

    public static synchronized ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection();
        }
        return instance;
    }

    private void connectToServer() {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Client đã kết nối đến máy chủ.");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle connection error appropriately
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle closing error appropriately
        }
    }
}
