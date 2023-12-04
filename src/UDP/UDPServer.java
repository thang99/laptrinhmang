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

public class UDPServer {
    static int port = 1234;
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket send, recv;
            int buffSize = 512;
            while(true){
                recv = new DatagramPacket(new byte[buffSize], buffSize);
                socket.receive(recv);
                String data = new String(recv.getData(), 0, recv.getLength());
                System.out.println("Server nhận: " + data);
                if(data.equals("bye"))
                    break;
                // Đảo ngược chuỗi và gửi lại client
                byte[] output= new StringBuilder(data).reverse().toString().getBytes();
                send = new DatagramPacket(output, output.length, recv.getAddress(), recv.getPort());
                socket.send(send);
            }
            System.out.println("Server đóng kết nối đến client " + recv.getAddress());
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
