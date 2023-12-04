package bai1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.*;
import java.text.NumberFormat;
import java.util.Locale;

public class bai1_3119560065_nguyenminhthang_server {
    public ServerSocket server = null;
    public Socket sk = null;
    public static BufferedReader inStream = null;
    public static BufferedWriter outStram = null;


    public static String getAPI(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute()
                    .parse();
            return doc.text();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public bai1_3119560065_nguyenminhthang_server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            sk = server.accept();
            outStram = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
            inStream = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            String line = "";
            while (!line.equals("Over")) {
                try {
                    line = inStream.readLine();
                    System.out.println("Server recived " + line);

                    if(isValid(line)){
                        String productID = Masanpham(line);
                        ProductAndReviews(productID);
                    }
                    else{
                        outStram.write("Url không hợp lệ");
                        outStram.newLine();
                        outStram.flush();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Closing conenction");
            inStream.close();
            outStram.close();
            sk.close();
            server.close();
        } catch (Exception e) {

        }
    }

    public static void ProductAndReviews(String idProduct) {
        try {
            String api = getAPI("https://tiki.vn/api/v2/products/" + idProduct);
            if(api == null){
                outStram.write("Không có dữ liệu!");
                outStram.newLine();
                outStram.flush();
                return;
            }
            JSONObject object = new JSONObject(api);
            String setNameProduct = "";
            int setPrice = 0;
            if (object.has("name")) {
                setNameProduct = (String) object.get("name");
            }
            if (object.has("price")) {
                setPrice = (int) object.get("price");
            }
            StringBuilder infoProduct = new StringBuilder();
            infoProduct.append("Tên sản Phẩm: " + setNameProduct+". ");
            infoProduct.append("Giá: " + formatCurrentcy(setPrice));
            infoProduct.append("\n");
            String review = getAPI("https://tiki.vn/api/v2/reviews?limit=10&include=comments,contribute_info,attribute_vote_summary&sort=score%7Cdesc,id%7Cdesc,stars%7Call&page=1&product_id=" + idProduct);
            JSONObject objReview = new JSONObject(review);
            JSONArray reviews = objReview.getJSONArray("data");
            JSONObject objStar = new JSONObject(review);
            infoProduct.append("Sản phẩm có: " + reviews.length() + " review!");
            infoProduct.append("\n");
            infoProduct.append("Dưới đây là các review về sản phẩm trên Tiki: ");
            for (Object jsonArrayItem : reviews) {
                infoProduct.append("\n");
                JSONObject item = (JSONObject) jsonArrayItem;
                infoProduct.append("+ " + item.get("content"));
            }
            System.out.println(infoProduct.toString());
            outStram.write(infoProduct.toString());
            outStram.newLine();
            outStram.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String Masanpham(String url) {
        int equalSignIndex = url.indexOf(".html");
        int startIndex  = 0;
        for(int i = equalSignIndex - 1 ; i > 0 ; i--){
            if(!isNumber(url.charAt(i) +"")){
                startIndex = i;
                break;
            }
        }
        String msp = url.substring(startIndex + 1,equalSignIndex);

        return msp;
    }


    public static boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (Exception e){
            return  false;
        }
    }

    public static String formatCurrentcy(int amount) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(amount);
    }

    public static void main(String[] args) {
        bai1_3119560065_nguyenminhthang_server server = new bai1_3119560065_nguyenminhthang_server(5000);
    }
}
