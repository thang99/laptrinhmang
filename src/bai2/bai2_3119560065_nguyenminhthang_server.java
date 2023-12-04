package bai2;

/**
 *
 * @author ACER
 */
import java.io.IOException;
import java.net.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class bai2_3119560065_nguyenminhthang_server {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        
        try {
            socket = new DatagramSocket(12345);
            System.out.println("Server is running...");
            
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                
                String requestData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String response = processRequest(requestData);
                
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
    
    private static String processRequest(String request) {
        String[] requestParts = request.split(";");
        if (requestParts.length !=2 && requestParts.length !=3) {
            return "Lỗi dữ liệu đầu vào";
        }
        
        String action1 = requestParts[0];
        String action2 = requestParts[1];
        String data = requestParts.length > 2 ? requestParts[2] : null;
        
        switch (action1) {
            case "enc":
                return encryptToMD5(action2);
            case "dec":
                return decryptMD5(action2);
            default:
                int sourceBase, targetBase;
            try {
                sourceBase = Integer.parseInt(action1);
                targetBase = Integer.parseInt(action2);
            } catch (NumberFormatException e) {
                return "Cơ số nguồn hoặc đích không hợp lệ";
            }
            return convertBase(data, sourceBase, targetBase);
        }
    }
    
    private static String encryptToMD5(String request) {
        String enc = null;
        try {
            String apiUrl = "https://hashtoolkit.com/generate-hash/?text=" + request;
            Connection.Response response = Jsoup.connect(apiUrl)
                    .method(Connection.Method.GET)
                    .followRedirects(true)
                    .execute();
            if (response.statusCode() == 200) {
            Document doc = response.parse();
            enc = doc.getElementsByClass("res-hash").first().text();
            } else {
            System.out.println("Lỗi: Mã trạng thái HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            }
        return "-->Chức năng băm MD5: "+ request + " --> " +  enc;
    }
    private static String decryptMD5(String request) {
        String dec = null;
        try {
            String apiUrl = "https://hashtoolkit.com/decrypt-hash/?hash=" + request;
            Connection.Response response = Jsoup.connect(apiUrl)
                    .method(Connection.Method.GET)
                    .followRedirects(true)
                    .execute();
            if (response.statusCode() == 200) {
            Document doc = response.parse();
            dec = doc.getElementsByClass("res-text").first().text();
            } else {
                System.out.println("Lỗi: Mã trạng thái HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            }
        return "-->Chức năng dịch ngược chuỗi MD5: "+ request + " --> " +  dec;
    }
    
    private static String convertBase(String data, int sourceBase, int targetBase) {
        try {
            // Chuyển đổi dữ liệu từ cơ số nguồn sang hệ thập phân
            int decimalValue = Integer.parseInt(data, sourceBase);

            // Chuyển đổi giá trị hệ thập phân sang hệ cơ số đích
            String convertedData = Integer.toString(decimalValue, targetBase);

            return "-->Chức năng đổi cơ số: " + data + " từ cơ số " + sourceBase + " sang cơ số " + targetBase + " --> " + convertedData;
        } catch (NumberFormatException e) {
            return "Lỗi dữ liệu đầu vào";
        }
    }
}

