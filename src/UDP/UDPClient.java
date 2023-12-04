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
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args){
        DatagramPacket send, recv;
        DatagramSocket socket;
        InetAddress host;
        int port = 1234;
        int buffSize = 512;
        try {
            socket = new DatagramSocket();
            host =  InetAddress.getByName("localhost");
            while (true) {
                System.out.printf("Nhập dữ liệu: ");
                Scanner input = new Scanner(System.in);
                String data = input.nextLine();
                send = new DatagramPacket(data.getBytes(), data.getBytes().length, host, port);
                socket.send(send);
                // Thoát chương trình nếu người dùng gõ bye
                if(data.equals("bye"))
                    break;
                // Nhận dữ liệu từ server gửi lại
                recv = new DatagramPacket(new byte[buffSize], buffSize);
                socket.receive(recv);
                String tmp = new String(recv.getData(), 0 , recv.getLength());
                System.out.println("Client nhận: " + tmp);

            }
            System.out.println("Client đóng kết nối");
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}