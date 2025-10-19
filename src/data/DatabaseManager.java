package data;

import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    // DOKTOR İŞLEMLERİ
    public static boolean doktorEkle(String doktorNo, String tcKimlik, String ad, String soyad,
                                     String uzmanlik, int poliklinikId, String unvan,
                                     String telefon, String email) {
        String sql = "INSERT INTO doktorlar (doktor_no, tc_kimlik, ad, soyad, uzmanlik, poliklinik_id, unvan, telefon, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doktorNo);
            pstmt.setString(2, tcKimlik);
            pstmt.setString(3, ad);
            pstmt.setString(4, soyad);
            pstmt.setString(5, uzmanlik);
            pstmt.setInt(6, poliklinikId);
            pstmt.setString(7, unvan);
            pstmt.setString(8, telefon);
            pstmt.setString(9, email);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Doktor ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean doktorGuncelle(int id, String doktorNo, String tcKimlik, String ad, String soyad,
                                         String uzmanlik, int poliklinikId, String unvan,
                                         String telefon, String email, boolean aktif) {
        String sql = "UPDATE doktorlar SET doktor_no=?, tc_kimlik=?, ad=?, soyad=?, uzmanlik=?, " +
                "poliklinik_id=?, unvan=?, telefon=?, email=?, aktif=? WHERE id=?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doktorNo);
            pstmt.setString(2, tcKimlik);
            pstmt.setString(3, ad);
            pstmt.setString(4, soyad);
            pstmt.setString(5, uzmanlik);
            pstmt.setInt(6, poliklinikId);
            pstmt.setString(7, unvan);
            pstmt.setString(8, telefon);
            pstmt.setString(9, email);
            pstmt.setBoolean(10, aktif);
            pstmt.setInt(11, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Doktor güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean doktorSil(int id) {
        String sql = "UPDATE doktorlar SET aktif = FALSE WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Doktor silme hatası: " + e.getMessage());
            return false;
        }
    }

    public static Object[] getDoktorById(int id) {
        String sql = "SELECT * FROM doktorlar WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Object[]{
                        rs.getInt("id"),
                        rs.getString("doktor_no"),
                        rs.getString("tc_kimlik"),
                        rs.getString("ad"),
                        rs.getString("soyad"),
                        rs.getString("uzmanlik"),
                        rs.getInt("poliklinik_id"),
                        rs.getString("unvan"),
                        rs.getString("telefon"),
                        rs.getString("email"),
                        rs.getBoolean("aktif")
                };
            }
        } catch (SQLException e) {
            System.out.println("Doktor getirme hatası: " + e.getMessage());
        }
        return null;
    }

    // HASTA İŞLEMLERİ
    public static boolean hastaEkle(String tcKimlik, String ad, String soyad, Date dogumTarihi,
                                    String cinsiyet, String telefon, String email, String adres) {
        String sql = "INSERT INTO hastalar (tc_kimlik, ad, soyad, dogum_tarihi, cinsiyet, telefon, email, adres) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tcKimlik);
            pstmt.setString(2, ad);
            pstmt.setString(3, soyad);
            pstmt.setDate(4, dogumTarihi);
            pstmt.setString(5, cinsiyet);
            pstmt.setString(6, telefon);
            pstmt.setString(7, email);
            pstmt.setString(8, adres);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Hasta ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean hastaGuncelle(int id, String tcKimlik, String ad, String soyad, Date dogumTarihi,
                                        String cinsiyet, String telefon, String email, String adres) {
        String sql = "UPDATE hastalar SET tc_kimlik=?, ad=?, soyad=?, dogum_tarihi=?, " +
                "cinsiyet=?, telefon=?, email=?, adres=? WHERE id=?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tcKimlik);
            pstmt.setString(2, ad);
            pstmt.setString(3, soyad);
            pstmt.setDate(4, dogumTarihi);
            pstmt.setString(5, cinsiyet);
            pstmt.setString(6, telefon);
            pstmt.setString(7, email);
            pstmt.setString(8, adres);
            pstmt.setInt(9, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Hasta güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean hastaSil(int id) {
        String sql = "DELETE FROM hastalar WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Hasta silme hatası: " + e.getMessage());
            return false;
        }
    }

    public static Object[] getHastaById(int id) {
        String sql = "SELECT * FROM hastalar WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Object[]{
                        rs.getInt("id"),
                        rs.getString("tc_kimlik"),
                        rs.getString("ad"),
                        rs.getString("soyad"),
                        rs.getDate("dogum_tarihi"),
                        rs.getString("cinsiyet"),
                        rs.getString("telefon"),
                        rs.getString("email"),
                        rs.getString("adres")
                };
            }
        } catch (SQLException e) {
            System.out.println("Hasta getirme hatası: " + e.getMessage());
        }
        return null;
    }

    // RANDEVU İŞLEMLERİ
    public static boolean randevuEkle(int hastaId, int doktorId, int poliklinikId,
                                      Timestamp randevuTarihi, String aciklama) {
        String sql = "INSERT INTO randevular (hasta_id, doktor_id, poliklinik_id, randevu_tarihi, aciklama) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hastaId);
            pstmt.setInt(2, doktorId);
            pstmt.setInt(3, poliklinikId);
            pstmt.setTimestamp(4, randevuTarihi);
            pstmt.setString(5, aciklama);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Randevu ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean randevuGuncelle(int id, int hastaId, int doktorId, int poliklinikId,
                                          Timestamp randevuTarihi, String durum, String aciklama) {
        String sql = "UPDATE randevular SET hasta_id=?, doktor_id=?, poliklinik_id=?, " +
                "randevu_tarihi=?, durumu=?, aciklama=? WHERE id=?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hastaId);
            pstmt.setInt(2, doktorId);
            pstmt.setInt(3, poliklinikId);
            pstmt.setTimestamp(4, randevuTarihi);
            pstmt.setString(5, durum);
            pstmt.setString(6, aciklama);
            pstmt.setInt(7, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Randevu güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean randevuSil(int id) {
        String sql = "DELETE FROM randevular WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Randevu silme hatası: " + e.getMessage());
            return false;
        }
    }

    public static Object[] getRandevuById(int id) {
        String sql = "SELECT * FROM randevular WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Object[]{
                        rs.getInt("id"),
                        rs.getInt("hasta_id"),
                        rs.getInt("doktor_id"),
                        rs.getInt("poliklinik_id"),
                        rs.getTimestamp("randevu_tarihi"),
                        rs.getString("durumu"),
                        rs.getString("aciklama")
                };
            }
        } catch (SQLException e) {
            System.out.println("Randevu getirme hatası: " + e.getMessage());
        }
        return null;
    }

    // POLİKLİNİK İŞLEMLERİ
    public static boolean poliklinikEkle(String ad, String aciklama, String kat, String telefon) {
        String sql = "INSERT INTO poliklinikler (ad, aciklama, kat, telefon) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ad);
            pstmt.setString(2, aciklama);
            pstmt.setString(3, kat);
            pstmt.setString(4, telefon);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Poliklinik ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean poliklinikGuncelle(int id, String ad, String aciklama, String kat, String telefon, boolean aktif) {
        String sql = "UPDATE poliklinikler SET ad=?, aciklama=?, kat=?, telefon=?, aktif=? WHERE id=?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ad);
            pstmt.setString(2, aciklama);
            pstmt.setString(3, kat);
            pstmt.setString(4, telefon);
            pstmt.setBoolean(5, aktif);
            pstmt.setInt(6, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Poliklinik güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean poliklinikSil(int id) {
        String sql = "UPDATE poliklinikler SET aktif = FALSE WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Poliklinik silme hatası: " + e.getMessage());
            return false;
        }
    }

    public static Object[] getPoliklinikById(int id) {
        String sql = "SELECT * FROM poliklinikler WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Object[]{
                        rs.getInt("id"),
                        rs.getString("ad"),
                        rs.getString("aciklama"),
                        rs.getString("kat"),
                        rs.getString("telefon"),
                        rs.getBoolean("aktif")
                };
            }
        } catch (SQLException e) {
            System.out.println("Poliklinik getirme hatası: " + e.getMessage());
        }
        return null;
    }

    // REÇETE İŞLEMLERİ
    public static boolean receteEkle(int hastaId, int doktorId, String ilacAdi, String dozaj,
                                     String kullanimSekli, String kullanimSikligi, String sure, String notlar) {
        String sql = "INSERT INTO receteler (hasta_id, doktor_id, ilac_adi, dozaj, kullanim_sekli, " +
                "kullanim_sikligi, sure, notlar, recete_tarihi) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE())";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hastaId);
            pstmt.setInt(2, doktorId);
            pstmt.setString(3, ilacAdi);
            pstmt.setString(4, dozaj);
            pstmt.setString(5, kullanimSekli);
            pstmt.setString(6, kullanimSikligi);
            pstmt.setString(7, sure);
            pstmt.setString(8, notlar);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Reçete ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean receteGuncelle(int id, String ilacAdi, String dozaj, String kullanimSekli,
                                         String kullanimSikligi, String sure, String notlar) {
        String sql = "UPDATE receteler SET ilac_adi=?, dozaj=?, kullanim_sekli=?, " +
                "kullanim_sikligi=?, sure=?, notlar=? WHERE id=?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ilacAdi);
            pstmt.setString(2, dozaj);
            pstmt.setString(3, kullanimSekli);
            pstmt.setString(4, kullanimSikligi);
            pstmt.setString(5, sure);
            pstmt.setString(6, notlar);
            pstmt.setInt(7, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Reçete güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    public static boolean receteSil(int id) {
        String sql = "DELETE FROM receteler WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Reçete silme hatası: " + e.getMessage());
            return false;
        }
    }

    public static Object[] getReceteById(int id) {
        String sql = "SELECT * FROM receteler WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Object[]{
                        rs.getInt("id"),
                        rs.getInt("hasta_id"),
                        rs.getInt("doktor_id"),
                        rs.getString("ilac_adi"),
                        rs.getString("dozaj"),
                        rs.getString("kullanim_sekli"),
                        rs.getString("kullanim_sikligi"),
                        rs.getString("sure"),
                        rs.getString("notlar")
                };
            }
        } catch (SQLException e) {
            System.out.println("Reçete getirme hatası: " + e.getMessage());
        }
        return null;
    }

    // LİSTELEME METODLARI (önceki koddaki gibi)
    public static List<Object[]> getDoktorlar() {
        List<Object[]> doktorlar = new ArrayList<>();
        String sql = "SELECT d.id, d.doktor_no, d.ad, d.soyad, d.uzmanlik, p.ad as poliklinik, " +
                "d.telefon, d.email, d.aktif " +
                "FROM doktorlar d LEFT JOIN poliklinikler p ON d.poliklinik_id = p.id " +
                "WHERE d.aktif = TRUE ORDER BY d.created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("doktor_no"),
                        rs.getString("ad"),
                        rs.getString("soyad"),
                        rs.getString("uzmanlik"),
                        rs.getString("poliklinik"),
                        rs.getString("telefon"),
                        rs.getString("email"),
                        rs.getBoolean("aktif") ? "Aktif" : "Pasif"
                };
                doktorlar.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Doktor listeleme hatası: " + e.getMessage());
        }
        return doktorlar;
    }

    public static List<Object[]> getHastalar() {
        List<Object[]> hastalar = new ArrayList<>();
        String sql = "SELECT id, tc_kimlik, ad, soyad, dogum_tarihi, cinsiyet, telefon, email, created_at " +
                "FROM hastalar ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("tc_kimlik"),
                        rs.getString("ad"),
                        rs.getString("soyad"),
                        rs.getDate("dogum_tarihi"),
                        rs.getString("cinsiyet"),
                        rs.getString("telefon"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                };
                hastalar.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Hasta listeleme hatası: " + e.getMessage());
        }
        return hastalar;
    }

    public static List<Object[]> getRandevular() {
        List<Object[]> randevular = new ArrayList<>();
        String sql = "SELECT r.id, h.ad as hasta_adi, h.soyad as hasta_soyad, " +
                "d.ad as doktor_adi, d.soyad as doktor_soyad, p.ad as poliklinik, " +
                "r.randevu_tarihi, r.durumu, r.aciklama " +
                "FROM randevular r " +
                "LEFT JOIN hastalar h ON r.hasta_id = h.id " +
                "LEFT JOIN doktorlar d ON r.doktor_id = d.id " +
                "LEFT JOIN poliklinikler p ON r.poliklinik_id = p.id " +
                "ORDER BY r.randevu_tarihi DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("hasta_adi") + " " + rs.getString("hasta_soyad"),
                        "Dr. " + rs.getString("doktor_adi") + " " + rs.getString("doktor_soyad"),
                        rs.getString("poliklinik"),
                        rs.getTimestamp("randevu_tarihi"),
                        rs.getString("durumu"),
                        rs.getString("aciklama")
                };
                randevular.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Randevu listeleme hatası: " + e.getMessage());
        }
        return randevular;
    }

    public static List<Object[]> getPoliklinikler() {
        List<Object[]> poliklinikler = new ArrayList<>();
        String sql = "SELECT p.id, p.ad, p.aciklama, p.kat, p.telefon, " +
                "COUNT(d.id) as doktor_sayisi " +
                "FROM poliklinikler p " +
                "LEFT JOIN doktorlar d ON p.id = d.poliklinik_id AND d.aktif = TRUE " +
                "WHERE p.aktif = TRUE " +
                "GROUP BY p.id, p.ad, p.aciklama, p.kat, p.telefon " +
                "ORDER BY p.ad";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("ad"),
                        rs.getString("aciklama"),
                        rs.getString("kat"),
                        rs.getString("telefon"),
                        rs.getInt("doktor_sayisi")
                };
                poliklinikler.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Poliklinik listeleme hatası: " + e.getMessage());
        }
        return poliklinikler;
    }

    public static List<Object[]> getReceteler() {
        List<Object[]> receteler = new ArrayList<>();
        String sql = "SELECT r.id, h.ad as hasta_adi, h.soyad as hasta_soyad, " +
                "d.ad as doktor_adi, d.soyad as doktor_soyad, " +
                "r.ilac_adi, r.dozaj, r.kullanim_sekli, r.sure, r.recete_tarihi " +
                "FROM receteler r " +
                "LEFT JOIN hastalar h ON r.hasta_id = h.id " +
                "LEFT JOIN doktorlar d ON r.doktor_id = d.id " +
                "ORDER BY r.recete_tarihi DESC, r.created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("hasta_adi") + " " + rs.getString("hasta_soyad"),
                        "Dr. " + rs.getString("doktor_adi") + " " + rs.getString("doktor_soyad"),
                        rs.getString("ilac_adi"),
                        rs.getString("dozaj"),
                        rs.getString("kullanim_sekli"),
                        rs.getString("sure"),
                        rs.getDate("recete_tarihi")
                };
                receteler.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Reçete listeleme hatası: " + e.getMessage());
        }
        return receteler;
    }

    // COMBOBOX ve İSTATİSTİK METODLARI (önceki koddaki gibi)
    public static List<Object[]> getHastalarForCombo() {
        List<Object[]> hastalar = new ArrayList<>();
        String sql = "SELECT id, ad, soyad FROM hastalar ORDER BY ad, soyad";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {rs.getInt("id"), rs.getString("ad") + " " + rs.getString("soyad")};
                hastalar.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Hasta combo hatası: " + e.getMessage());
        }
        return hastalar;
    }

    public static List<Object[]> getDoktorlarForCombo() {
        List<Object[]> doktorlar = new ArrayList<>();
        String sql = "SELECT id, ad, soyad FROM doktorlar WHERE aktif = TRUE ORDER BY ad, soyad";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {rs.getInt("id"), "Dr. " + rs.getString("ad") + " " + rs.getString("soyad")};
                doktorlar.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Doktor combo hatası: " + e.getMessage());
        }
        return doktorlar;
    }

    public static List<Object[]> getPolikliniklerForCombo() {
        List<Object[]> poliklinikler = new ArrayList<>();
        String sql = "SELECT id, ad FROM poliklinikler WHERE aktif = TRUE ORDER BY ad";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {rs.getInt("id"), rs.getString("ad")};
                poliklinikler.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Poliklinik combo hatası: " + e.getMessage());
        }
        return poliklinikler;
    }

    // İSTATİSTİKLER
    public static int getToplamDoktor() {
        return getCount("SELECT COUNT(*) FROM doktorlar WHERE aktif = TRUE");
    }

    public static int getToplamHasta() {
        return getCount("SELECT COUNT(*) FROM hastalar");
    }

    public static int getBugunkuRandevu() {
        return getCount("SELECT COUNT(*) FROM randevular WHERE DATE(randevu_tarihi) = CURDATE()");
    }

    public static int getToplamPoliklinik() {
        return getCount("SELECT COUNT(*) FROM poliklinikler WHERE aktif = TRUE");
    }

    public static int getAktifRandevular() {
        return getCount("SELECT COUNT(*) FROM randevular WHERE durumu = 'BEKLEMEDE'");
    }

    public static int getToplamRecete() {
        return getCount("SELECT COUNT(*) FROM receteler");
    }

    private static int getCount(String sql) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("İstatistik hatası: " + e.getMessage());
        }
        return 0;
    }
}