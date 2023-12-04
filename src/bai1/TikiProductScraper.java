package bai1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class TikiProductScraper {
    public static void main(String[] args) {
        String productUrl = "https://tiki.vn/api/v2/products/189371737"; // Thay thế URL này bằng URL sản phẩm cụ thể trên Tiki

        try {
            Document document = Jsoup.connect(productUrl).get();
            String productName = document.select("name").text();
            String productPrice = document.select("price").text();

            System.out.println("Tên sản phẩm: " + productName);
            System.out.println("Giá sản phẩm: " + productPrice);

            // Các thông tin khác có thể được lấy tại đây bằng cách tìm các phần tử HTML và thuộc tính phù hợp.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
