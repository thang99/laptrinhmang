/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

/**
 *
 * @author ACER
 */
import java.net.*;
import java.util.Scanner;
import java.io.IOException;
public class client {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 12345;

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("Nhập số nguyên dương n: ");
                String data = sc.nextLine();
                if (data.matches("\\d+")) {
                    int n = Integer.parseInt(data);

                    byte[] sendData = data.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                    clientSocket.send(sendPacket);

                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String tmp = new String(receivePacket.getData(), 0 , receivePacket.getLength());
          
                    System.out.println("Số lượng số có tổng các chữ số cộng lại bằng " + n + ": " + tmp);
                } else {
                    System.out.println("Vui lòng nhập một số nguyên dương.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
