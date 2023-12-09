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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewClass {
    public static void main(String[] args) {
        // Thông tin kết nối đến SQL Server
        String url = "jdbc:sqlserver://localhost:1433;databaseName=laptrinhmang";
        String username = "sa";
        String password = "thang2822001";
        String path = "C:\\Users\\ACER\\Pictures\\hieuthu21.png";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Query SQL để thêm dữ liệu
            String sql = "INSERT INTO ImagePaths VALUES(?,?)";

            // Tạo một PreparedStatement để thực hiện truy vấn SQL
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Thiết lập các tham số trong truy vấn SQL với giá trị thực tế
                preparedStatement.setString(1, path);
                preparedStatement.setString(2, "hth");
                

                // Thực thi truy vấn SQL để thêm dữ liệu
                int rowsAffected = preparedStatement.executeUpdate();

                // Kiểm tra xem dữ liệu đã được thêm thành công hay không
                if (rowsAffected > 0) {
                    System.out.println("Dữ liệu đã được thêm thành công!");
                } else {
                    System.out.println("Không thể thêm dữ liệu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
