package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbTest {
    public static void main(String[] args) {
        // MySQL bilgilerini buraya yaz
        String url = "jdbc:mysql://localhost:3306/hastane_randevu_pro"; // DB adı doğru olmalı
        String user = "root"; // kullanıcı adı
        String password = "47749099"; // şifren

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("✅ Bağlantı başarılı!");
            } else {
                System.out.println("❌ Bağlantı başarısız!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Bağlantı hatası:");
            e.printStackTrace();
        }
    }
}
