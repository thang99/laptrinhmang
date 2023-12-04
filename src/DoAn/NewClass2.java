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
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewClass2 {

    public static void main(String[] args) throws IOException {
        // Thông tin API
        String apiKey = "66Zg2u84wpWphGSnBh0hV0ls-gT3f74g";
        String apiSecret = "vcInXSlnXaxk-bbbOxY9P9j6mXeJ6Z_Y";

        // Đường dẫn đến hai hình ảnh khuôn mặt cần so sánh
        String image1Path = "C:\\Users\\ACER\\Pictures\\IMG_2672.JPG";
        String image2Path = "C:\\Users\\ACER\\Pictures\\IMG_2673.JPG";

        // Định dạng URL API
        String apiUrl = "https://api-us.faceplusplus.com/facepp/v3/compare";

        // Xây dựng nội dung yêu cầu
        String boundary = "Boundary-" + System.currentTimeMillis();
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        BufferedReader reader = null;

        try {
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

        } finally {
            // Đóng các luồng và kết nối khi đã sử dụng xong
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}


