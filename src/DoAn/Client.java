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
import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try (
            Socket socket = new Socket(serverAddress, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            ) {
            // Nhập đường dẫn ảnh từ người dùng
            String imagePath1 = "C:\\Users\\ACER\\Pictures\\IMG_2672.JPG";
            

            // Gửi đường dẫn ảnh đến server
            out.println(imagePath1);
            

            // Nhận và in kết quả từ server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String result = in.readLine();
            System.out.println("Phản hồi từ server: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}