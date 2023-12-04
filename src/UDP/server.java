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
import org.json.JSONObject;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class server {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(12345);

            // Đọc dữ liệu từ tệp data.txt
            List<Integer> data = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ACER\\Desktop\\file.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    data.add(Integer.parseInt(line));
                }
            }

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String nString = new String(receivePacket.getData(), 0, receivePacket.getLength());
                int targetSum = Integer.parseInt(nString);

                int result = countNumbersWithDigitSum(data, targetSum);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();



                byte[] sendData = new StringBuilder(nString).reverse().toString().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);

                System.out.println("Sent the result " + result + " to " + clientAddress.getHostAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int countNumbersWithDigitSum(List<Integer> data, int targetSum) {
        int count = 0;
        for (int number : data) {
            int digitSum = calculateDigitSum(number);
            if (digitSum == targetSum) {
                count++;
            }
        }
        return count;
    }

    private static int calculateDigitSum(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}