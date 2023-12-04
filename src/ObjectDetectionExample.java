import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ObjectDetectionExample {
    public static void main(String[] args) throws IOException {
        String apiUrl = "https://api.edenai.run/v2/image/object_detection";
        String apiKey = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiODgyM2Y4MjgtZDVlZi00ODEzLThlYTYtNmZiMDQ3MjdhYzEzIiwidHlwZSI6ImFwaV90b2tlbiJ9.l7z8SZKk69WtsX62WK5MTOxQSm1liS2lzHBkvHilvF0";
        String filePath = "C:\\Users\\ACER\\Pictures\\OIP (1).jpg";
        String providers = "amazon";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", apiKey);

        String boundary = "***";
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
        } finally {
            connection.disconnect();
        }
    }
}
