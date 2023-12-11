package base64;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class base64 {
    private static void decodeBase64AndWriteImage(String base64String, String imagePath) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64String);
            Path path = Paths.get(imagePath);
            Files.write(path, imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertImageToBase64(String imagePath) {
        String base64String = "";
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            base64String = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64String;
    }

    public static void main(String[] args) {
        String imagePath = "C:\\Users\\ACER\\Pictures\\hieuthu21.png";
        String base64String = convertImageToBase64(imagePath);

        String imagePathReceived = "C:\\Users\\ACER\\Pictures\\a.png";

        decodeBase64AndWriteImage(base64String, imagePathReceived);

        System.out.println("Image has been decoded and saved to: " + imagePath);
        System.out.println("Base64 representation of the image:\n" + base64String);
    }

}