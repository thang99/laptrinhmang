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
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import org.json.JSONObject;
import java.sql.*;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 12345;
    private static final int MAX_CLIENTS = 10;
    private static ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    @Override
    
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {           
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.startsWith("km")){
                    String Path1 = inputLine.replace("km", "").trim();
                    // Đọc đường dẫn ảnh từ client
                    String imagePath1 = Path1;
                    String imagePath2 = getImagePathFromDatabase();

                    // Thực hiện so sánh khuôn mặt
                    String[] imagePathsArray = imagePath2.split("[\\s,;]+");
                    List<String> imagePathsList = Arrays.asList(imagePathsArray);
                    boolean foundResult = false;// Biến để kiểm tra xem có ít nhất một kết quả nào >= 70 không.
                    // Thực hiện so sánh khuôn mặt cho từng đường dẫn ảnh
                    for (String path : imagePathsList) {
                        double result = performFaceComparison(imagePath1, path);
                        if ( result >=70.0) {
                            String name = getNameFromDatabase(path);
                            System.out.println(path+","+name+","+result);
                            out.println(path + ";" + name + ";" + result);
                            foundResult = true;
                        }
                    }
                    // Nếu không có kết quả nào >= 70, trả về null
                    if (!foundResult) {
                        out.println("null");
                    }
                }
                else if(inputLine.startsWith("dt")){
                    String Path = inputLine.replace("dt", "").trim();
                    String Object = ObjectDetection(Path);
                    out.print(Object);
                }
                else if(inputLine.startsWith("add")){
                    String them = inputLine.replace("add", "");
                    String[] parts = them.split(";");
                    String receivedPath = parts[0];
                    String receivedName = parts[1];                   
                    String themdtb = insertNameToDatabase(receivedPath, receivedName);
                    out.println(themdtb);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static String getNameFromDatabase(String path){    
        String name = null;
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=laptrinhmang";
            String username = "sa";
            String password = "thang2822001";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Thực hiện truy vấn để lấy đường dẫn ảnh thứ hai
            String query = "SELECT Name FROM ImagePaths WHERE FilePath = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, path);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("Name");
                System.out.println("name: " + name);
            }
        }
        // Đóng tài nguyên
        connection.close();

        } catch (Exception e) {
             e.printStackTrace();
        }
        return name;
    }
    
    private static String insertNameToDatabase(String Path, String name){
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=laptrinhmang";
            String username = "sa";
            String password = "thang2822001"; 
            Connection connection = DriverManager.getConnection(url, username, password);      
            String query = "INSERT INTO ImagePaths VALUES(?,?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, Path);
            pst.setString(2, name);

        } catch (Exception e) {
            return "Thêm thất bại";
        }
        return "Thêm thành công!";
    }
    
    private static String getImagePathFromDatabase() {
        StringBuilder pathsBuilder = new StringBuilder();
        String imagePath = null;

        try {
            // Thiết lập kết nối đến cơ sở dữ liệu
            String url = "jdbc:sqlserver://localhost:1433;databaseName=laptrinhmang";
            String username = "sa";
            String password = "thang2822001";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Thực hiện truy vấn để lấy đường dẫn ảnh thứ hai
            String query = "SELECT FilePath FROM ImagePaths";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Trích xuất đường dẫn ảnh từ tập kết quả
            while (resultSet.next()) {
                    imagePath = resultSet.getString("FilePath");
                    System.out.println("Image Path: " + imagePath);
                    pathsBuilder.append(imagePath).append(" ");
            }
            // Đóng tài nguyên
            resultSet.close();
            statement.close();
            connection.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return pathsBuilder.toString().trim();
    }
    private static double performFaceComparison(String imagePath1, String imagePath2) {
        try {
            String apiKey = "66Zg2u84wpWphGSnBh0hV0ls-gT3f74g";
            String apiSecret = "vcInXSlnXaxk-bbbOxY9P9j6mXeJ6Z_Y";

            // Đường dẫn đến hai hình ảnh khuôn mặt cần so sánh
            String image1Path = imagePath1;
            String image2Path = imagePath2;

            // Định dạng URL API
            String apiUrl = "https://api-us.faceplusplus.com/facepp/v3/compare";

            // Xây dựng nội dung yêu cầu
            String boundary = "Boundary-" + System.currentTimeMillis();
            String lineEnd = "\r\n";
            String twoHyphens = "--";

            HttpURLConnection connection = null;
            DataOutputStream outputStream = null;
            BufferedReader reader = null;


                URL url = new URL(apiUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                outputStream = new DataOutputStream(connection.getOutputStream());

                // Thêm thông tin API và hình ảnh vào yêu cầu
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"api_key\"" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(apiKey + lineEnd);

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"api_secret\"" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(apiSecret + lineEnd);

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"image_file1\";filename=\"image1.jpg\"" + lineEnd);
                outputStream.writeBytes(lineEnd);
                FileInputStream fileInputStream1 = new FileInputStream(new File(image1Path));
                byte[] buffer1 = new byte[4096];
                int bytesRead1;
                while ((bytesRead1 = fileInputStream1.read(buffer1)) != -1) {
                    outputStream.write(buffer1, 0, bytesRead1);
                }
                outputStream.writeBytes(lineEnd);

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"image_file2\";filename=\"image2.jpg\"" + lineEnd);
                outputStream.writeBytes(lineEnd);
                FileInputStream fileInputStream2 = new FileInputStream(new File(image2Path));
                byte[] buffer2 = new byte[4096];
                int bytesRead2;
                while ((bytesRead2 = fileInputStream2.read(buffer2)) != -1) {
                    outputStream.write(buffer2, 0, bytesRead2);
                }
                outputStream.writeBytes(lineEnd);

                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Đọc phản hồi từ máy chủ
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseStringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseStringBuilder.append(line).append("\n");
                }
                String responseData = responseStringBuilder.toString();
                JSONObject jsonResponse = new JSONObject(responseData);

                // Xử lý kết quả
                double confidence = jsonResponse.optDouble("confidence", 0.0);
                System.out.println("Độ chính xác của sự giống nhau giữa hai khuôn mặt là: " + confidence + "%");
                return confidence;

            } catch (IOException ex) {
            return -1.0;
        } 
    }
    private static String ObjectDetection(String imagePath){
        try {
            String apiUrl = "https://api.edenai.run/v2/image/object_detection";
            String apiKey = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiODgyM2Y4MjgtZDVlZi00ODEzLThlYTYtNmZiMDQ3MjdhYzEzIiwidHlwZSI6ImFwaV90b2tlbiJ9.l7z8SZKk69WtsX62WK5MTOxQSm1liS2lzHBkvHilvF0";
            String filePath = imagePath;
            String providers = "amazon";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", apiKey);

            String boundary = "*****";
            String lineEnd = "\r\n";
            String twoHyphens = "--";

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            try (DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                 FileInputStream fileInputStream = new FileInputStream(filePath)) {

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"providers\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(providers + lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + filePath + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = fileInputStream.available();
                int bufferSize = Math.min(bytesAvailable, 1024);
                byte[] buffer = new byte[bufferSize];

                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    dos.write(buffer, 0, bytesRead);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        System.out.println(response.toString());
                    }
                } else {
                    System.out.println("Error: " + responseCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }   
        return null;
    }
}