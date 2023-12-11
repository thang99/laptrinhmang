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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


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

            // Nhận dữ liệu từ client
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            // Đọc từ khóa "km"
            byte[] keywordBuffer = new byte[2];
            inputStream.read(keywordBuffer);
            String keyword = new String(keywordBuffer);
            
            if ("km".equals(keyword)) {
                System.out.println("Client yêu cầu chức năng nhận diện khuôn mặt.");
                
                // Create a Random object
                Random random = new Random();

                // Generate a random integer
                int randomNumber = random.nextInt();
                
                String imagePath1 = "C:\\Users\\HoangPhi\\Desktop\\temp" + String.valueOf(randomNumber) + ".jpg";
                System.out.println(imagePath1);
                
                receiveImage(inputStream, imagePath1);
                
//                // Đọc đường dẫn ảnh từ client
//                String imagePath2 = getImagePathFromDatabase();
//
//                // Thực hiện so sánh khuôn mặt
//                String[] imagePathsArray = imagePath2.split("[\\s,;]+");
//                List<String> imagePathsList = Arrays.asList(imagePathsArray);
//                boolean foundResult = false;// Biến để kiểm tra xem có ít nhất một kết quả nào >= 70 không.
//                // Thực hiện so sánh khuôn mặt cho từng đường dẫn ảnh
//                for (String path : imagePathsList) {
//                    double result = performFaceComparison(imagePath1, path);
//                    if ( result >=70.0) {
//                        String name = getNameFromDatabase(path);
//                        System.out.println(path+","+name+","+result);
//                        out.println(path + ";" + name + ";" + result);
//                        foundResult = true;
//                    }
//                }
//                // Nếu không có kết quả nào >= 70, trả về null
//                if (!foundResult) {
//                    out.println("null");
//                }
                
            } else if ("dt".equals(keyword)) {
                System.out.println("Client yêu cầu chức năng nhận diện đối tượng.");
                String imagePath = "C:\\Users\\HoangPhi\\Desktop\\received_image.jpg";
                
                File receivedImageFile = new File(imagePath);
                
                // Ghi dữ liệu hình ảnh vào file received_image
                receiveImage(inputStream, imagePath);

                // Call your object detection method
                List<Item> detectedObjects = ObjectDetection(imagePath);
                
                // Load the original image
                BufferedImage originalImage = loadImage(imagePath);
                
                // Draw rectangles and display object info
                drawRectanglesWithInfo(originalImage, detectedObjects);

                // Save the modified image
                String imagePathTemp = "C:\\Users\\HoangPhi\\Desktop\\image_with_objects.jpg";
                saveImage(originalImage, imagePathTemp);
                
                //Gửi hình ảnh về Client
                sendImage(outputStream, imagePathTemp);
               
            } else {
                System.out.println("Invalid keyword. Image not received");
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
    
    private void receiveImage(InputStream inputStream, String imagePath) {
        try {
            File receivedImageFile = new File(imagePath);
            FileOutputStream fileOutputStream = new FileOutputStream(receivedImageFile);

            // Ghi dữ liệu hình ảnh vào file received_image
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Image received and saved successfully.");
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    private void sendImage(OutputStream outputStream, String imagePath) {
    try {
        // Mở một luồng riêng để gửi dữ liệu hình ảnh
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Image sent successfully.");

        } catch (IOException e) {
            System.err.println("Error sending image: " + e);
        }
    } catch (Exception e) {
        e.printStackTrace();
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
            System.out.println(Path+";"+name);
            String url = "jdbc:sqlserver://localhost:1433;databaseName=laptrinhmang";
            String username = "sa";
            String password = "thang2822001"; 
            Connection connection = DriverManager.getConnection(url, username, password);      
            String query = "INSERT INTO ImagePaths VALUES(?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Thiết lập các tham số trong truy vấn SQL với giá trị thực tế
                preparedStatement.setString(1, Path);
                preparedStatement.setString(2, name);
                

                // Thực thi truy vấn SQL để thêm dữ liệu
                int rowsAffected = preparedStatement.executeUpdate();

                // Kiểm tra xem dữ liệu đã được thêm thành công hay không
                if (rowsAffected > 0) {
                    System.out.println("Dữ liệu đã được thêm thành công!");
                } else {
                    System.out.println("Không thể thêm dữ liệu.");
                }
            }
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

    private void drawRectanglesWithInfo(BufferedImage image, List<Item> detectedItems) {
        // Vẽ hình chữ nhật và hiển thị thông tin
        Graphics2D g2d = image.createGraphics();

        // Draw rectangles and display object info
        for (Item item : detectedItems) {
            drawRectangle(image, g2d, item.getXMin(), item.getYMin(), item.getXMax(), item.getYMax(), Color.RED);
            displayObjectInfo(image, g2d, item);
        }

        g2d.dispose();
    }

    private void drawRectangle(BufferedImage image, Graphics2D g2d, double xMin, double yMin, double xMax, double yMax, Color color) {
        int x = (int) (xMin * image.getWidth());
        int y = (int) (yMin * image.getHeight());
        int rectWidth = (int) ((xMax - xMin) * image.getWidth());
        int rectHeight = (int) ((yMax - yMin) * image.getHeight());
        
        // Đặt độ dày của đường vẽ
        g2d.setStroke(new BasicStroke(3));
        
        g2d.setColor(color);
        g2d.drawRect(x, y, rectWidth, rectHeight);
    }

    private void displayObjectInfo(BufferedImage image, Graphics2D g2d, Item items) {
        int x = (int) (items.getXMin() * image.getWidth());
        int y = (int) (items.getYMin() * image.getHeight());
        
        Font font = new Font("Arial", Font.PLAIN, 40);
        g2d.setFont(font);
        
        g2d.setColor(Color.YELLOW);
        long confidence = Math.round(items.getConfidence() * 100);
        String confidenceString = Long.toString(confidence) + "%";
        g2d.drawString(items.getLabel() + " " + confidenceString, x, y - 5);
    }

    private void saveImage(BufferedImage image, String outputPath) {
        try {
            File output = new File(outputPath);
            ImageIO.write(image, "jpg", output);
            System.out.println("Image with info saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private List<Item> ObjectDetection(String imagePath){
        try {
            String apiUrl = "https://api.edenai.run/v2/image/object_detection";
            String apiKey = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiODgyM2Y4MjgtZDVlZi00ODEzLThlYTYtNmZiMDQ3MjdhYzEzIiwidHlwZSI6ImFwaV90b2tlbiJ9.l7z8SZKk69WtsX62WK5MTOxQSm1liS2lzHBkvHilvF0";
            String filePath = imagePath;
            String providers = "amazon";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", apiKey);
            connection.setRequestProperty("Accept", "application/json");
                
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
                    System.out.println("Kết nối đến API thành công");
                    /// Đọc và xử lý phản hồi JSON
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        
                        System.out.println("JSON trả về sau khi gọi API: ");
                        System.out.println(response.toString());
                        
                        // Phân tích JSON response
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        JSONObject jsonProvider = jsonResponse.getJSONObject("amazon");
                        JSONArray itemsArray = jsonProvider.getJSONArray("items");

                        // Trích xuất thông tin từ mảng items
                        List<Item> itemsList = new ArrayList<>();
                        for (int i = 0; i < itemsArray.length(); i++) {
                            JSONObject item = itemsArray.getJSONObject(i);
                            String label = item.getString("label");
                            Double confidence = item.getDouble("confidence");
                            Double x_min = item.isNull("x_min") ? null : item.getDouble("x_min");
                            Double x_max = item.isNull("x_max") ? null : item.getDouble("x_max");
                            Double y_min = item.isNull("y_min") ? null : item.getDouble("y_min");
                            Double y_max = item.isNull("y_max") ? null : item.getDouble("y_max");
                            
                            if (x_min == null || x_max == null || y_min == null || y_max == null) {
                                continue;
                            }
                            Item newItem = new Item(label, confidence, x_min, x_max, y_min, y_max);
                            itemsList.add(newItem);
                        }

                        // In danh sách items
                        for (Item items : itemsList) {
                            System.out.println("Label: "+ items.getLabel());
                            System.out.println("Confidence: " + items.getConfidence());
                            System.out.println("x_min: " + items.getXMin());
                            System.out.println("x_max: " + items.getXMax());
                            System.out.println("y_min: " + items.getYMin());
                            System.out.println("y_max: " + items.getYMax());
                            System.out.println("-----------");
                        }

                        return itemsList;

                    }
                    finally {
                        connection.disconnect();
                    }
                } else {
                    System.out.println("Kết nối đến API thất bại");
                    System.out.println("Error: " + responseCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}