// config/DatabaseConfig.java
package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConfig {
    private static final String CONFIG_FILE = "config.properties";
    private static String URL;
    private static String DB_NAME;
    private static String USERNAME;
    private static String PASSWORD;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            URL = props.getProperty("db.url", "jdbc:mysql://localhost:3306/");
            DB_NAME = props.getProperty("db.name", "hastane_randevu_pro");
            USERNAME = props.getProperty("db.username", "root");
            PASSWORD = props.getProperty("db.password", "47749099");
        } catch (IOException e) {
            System.out.println("⚠️ config.properties okunamadı, varsayılan değerler kullanılacak.");
            URL = "jdbc:mysql://localhost:3306/";
            DB_NAME = "hastane_randevu_pro";
            USERNAME = "Kullanıcı adınız";
            PASSWORD = "Şifreniz";
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Veritabanını oluştur ve tabloları hazırla
            createDatabaseIfNotExists();

            // Asıl veritabanına bağlan
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USERNAME, PASSWORD);
            System.out.println("✅ Veritabanına başarıyla bağlanıldı: " + DB_NAME);
            return conn;

        } catch (ClassNotFoundException e) {
            throw new SQLException("❌ MySQL JDBC Driver bulunamadı!", e);
        }
    }

    private static void createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("✅ Veritabanı kontrol edildi/oluşturuldu: " + DB_NAME);

            createTables(conn);

        } catch (SQLException e) {
            System.err.println("❌ Veritabanı oluşturma hatası: " + e.getMessage());
        }
    }

    private static void createTables(Connection conn) {
        String[] tables = {
                "CREATE TABLE IF NOT EXISTS poliklinikler (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "ad VARCHAR(100) NOT NULL UNIQUE," +
                        "aciklama TEXT," +
                        "kat VARCHAR(20)," +
                        "telefon VARCHAR(20)," +
                        "aktif BOOLEAN DEFAULT TRUE," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ")",

                "CREATE TABLE IF NOT EXISTS doktorlar (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "doktor_no VARCHAR(20) UNIQUE NOT NULL," +
                        "tc_kimlik VARCHAR(11) UNIQUE," +
                        "ad VARCHAR(50) NOT NULL," +
                        "soyad VARCHAR(50) NOT NULL," +
                        "uzmanlik VARCHAR(100)," +
                        "poliklinik_id INT," +
                        "unvan VARCHAR(50)," +
                        "telefon VARCHAR(20)," +
                        "email VARCHAR(100)," +
                        "maas DECIMAL(10,2)," +
                        "baslama_tarihi DATE," +
                        "aktif BOOLEAN DEFAULT TRUE," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (poliklinik_id) REFERENCES poliklinikler(id)" +
                        ")",

                "CREATE TABLE IF NOT EXISTS hastalar (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "tc_kimlik VARCHAR(11) UNIQUE NOT NULL," +
                        "ad VARCHAR(50) NOT NULL," +
                        "soyad VARCHAR(50) NOT NULL," +
                        "dogum_tarihi DATE," +
                        "cinsiyet VARCHAR(10)," +
                        "telefon VARCHAR(20)," +
                        "email VARCHAR(100)," +
                        "adres TEXT," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ")",

                "CREATE TABLE IF NOT EXISTS randevular (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "hasta_id INT," +
                        "doktor_id INT," +
                        "poliklinik_id INT," +
                        "randevu_tarihi DATETIME NOT NULL," +
                        "durumu VARCHAR(20) DEFAULT 'BEKLEMEDE'," +
                        "aciklama TEXT," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (hasta_id) REFERENCES hastalar(id)," +
                        "FOREIGN KEY (doktor_id) REFERENCES doktorlar(id)," +
                        "FOREIGN KEY (poliklinik_id) REFERENCES poliklinikler(id)," +
                        "UNIQUE KEY unique_randevu (doktor_id, randevu_tarihi)" +
                        ")",

                "CREATE TABLE IF NOT EXISTS receteler (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "randevu_id INT," +
                        "doktor_id INT," +
                        "hasta_id INT," +
                        "ilaclar TEXT NOT NULL," +
                        "dozaj VARCHAR(100)," +
                        "kullanim_sekli VARCHAR(100)," +
                        "notlar TEXT," +
                        "recete_tarihi DATETIME," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (randevu_id) REFERENCES randevular(id)," +
                        "FOREIGN KEY (doktor_id) REFERENCES doktorlar(id)," +
                        "FOREIGN KEY (hasta_id) REFERENCES hastalar(id)" +
                        ")",

                "CREATE TABLE IF NOT EXISTS receteler (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "randevu_id INT," +
                        "doktor_id INT," +
                        "hasta_id INT," +
                        "ilac_adi VARCHAR(200) NOT NULL," +
                        "dozaj VARCHAR(100)," +
                        "kullanim_sekli VARCHAR(200)," +
                        "kullanim_sikligi VARCHAR(100)," +
                        "sure VARCHAR(50)," +
                        "notlar TEXT," +
                        "recete_tarihi DATE," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (randevu_id) REFERENCES randevular(id)," +
                        "FOREIGN KEY (doktor_id) REFERENCES doktorlar(id)," +
                        "FOREIGN KEY (hasta_id) REFERENCES hastalar(id)" +
                        ")",
        };

        try (Statement stmt = conn.createStatement()) {
            for (String sql : tables) stmt.execute(sql);
            System.out.println("✅ Tüm tablolar kontrol edildi/oluşturuldu.");
            seedSampleData(conn);
        } catch (SQLException e) {
            System.err.println("❌ Tablo oluşturma hatası: " + e.getMessage());
        }
    }

    private static void seedSampleData(Connection conn) {
        String[] inserts = {
                "INSERT IGNORE INTO poliklinikler (ad, aciklama, kat, telefon) VALUES " +
                        "('Kardiyoloji', 'Kalp ve damar hastalıkları', '2. Kat', '0212 111 1111'), " +
                        "('Nöroloji', 'Beyin ve sinir hastalıkları', '3. Kat', '0212 222 2222'), " +
                        "('Dahiliye', 'İç hastalıkları', '1. Kat', '0212 333 3333')",

                "INSERT IGNORE INTO doktorlar (doktor_no, tc_kimlik, ad, soyad, uzmanlik, poliklinik_id, unvan, telefon, email) VALUES " +
                        "('DOC001', '11111111111', 'Ahmet', 'Yılmaz', 'Kardiyoloji', 1, 'Prof. Dr.', '0555 111 1111', 'ahmet.yilmaz@hastane.com'), " +
                        "('DOC002', '22222222222', 'Ayşe', 'Demir', 'Nöroloji', 2, 'Doç. Dr.', '0555 222 2222', 'ayse.demir@hastane.com'), " +
                        "('DOC003', '33333333333', 'Mehmet', 'Kaya', 'Dahiliye', 3, 'Uzm. Dr.', '0555 333 3333', 'mehmet.kaya@hastane.com')"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String data : inserts) stmt.execute(data);
            System.out.println("✅ Örnek veriler eklendi (varsa).");
        } catch (SQLException e) {
            System.err.println("⚠️ Örnek veri ekleme hatası: " + e.getMessage());
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("🔒 Bağlantı kapatıldı.");
            } catch (SQLException e) {
                System.err.println("❌ Bağlantı kapatma hatası: " + e.getMessage());
            }
        }
    }
}
