// repository/impl/DoktorRepositoryImpl.java
package repository.impl;

import repository.DoktorRepository;
import model.Doktor;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoktorRepositoryImpl implements DoktorRepository {

    @Override
    public List<Doktor> findAll() {
        List<Doktor> doktorlar = new ArrayList<>();
        String sql = "SELECT * FROM doktorlar ORDER BY ad, soyad";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                doktorlar.add(mapResultSetToDoktor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Doktor listeleme hatası: " + e.getMessage());
        }
        return doktorlar;
    }

    @Override
    public Optional<Doktor> findById(int id) {
        String sql = "SELECT * FROM doktorlar WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToDoktor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Doktor bulma hatası: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Doktor> findByDoktorNo(String doktorNo) {
        String sql = "SELECT * FROM doktorlar WHERE doktor_no = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doktorNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToDoktor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Doktor no ile bulma hatası: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Doktor> findByPoliklinikId(int poliklinikId) {
        List<Doktor> doktorlar = new ArrayList<>();
        String sql = "SELECT * FROM doktorlar WHERE poliklinik_id = ? AND aktif = TRUE ORDER BY ad, soyad";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, poliklinikId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                doktorlar.add(mapResultSetToDoktor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Poliklinik doktorları bulma hatası: " + e.getMessage());
        }
        return doktorlar;
    }

    @Override
    public List<Doktor> findByAktif(boolean aktif) {
        List<Doktor> doktorlar = new ArrayList<>();
        String sql = "SELECT * FROM doktorlar WHERE aktif = ? ORDER BY ad, soyad";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, aktif);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                doktorlar.add(mapResultSetToDoktor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Aktif doktorlar bulma hatası: " + e.getMessage());
        }
        return doktorlar;
    }

    @Override
    public Doktor save(Doktor doktor) {
        String sql = "INSERT INTO doktorlar (doktor_no, tc_kimlik, ad, soyad, uzmanlik, poliklinik_id, unvan, telefon, email, maas, baslama_tarihi, aktif) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, doktor.getDoktorNo());
            pstmt.setString(2, doktor.getTcKimlik());
            pstmt.setString(3, doktor.getAd());
            pstmt.setString(4, doktor.getSoyad());
            pstmt.setString(5, doktor.getUzmanlik());
            pstmt.setInt(6, doktor.getPoliklinikId());
            pstmt.setString(7, doktor.getUnvan());
            pstmt.setString(8, doktor.getTelefon());
            pstmt.setString(9, doktor.getEmail());
            pstmt.setDouble(10, doktor.getMaas());

            if (doktor.getBaslamaTarihi() != null) {
                pstmt.setDate(11, Date.valueOf(doktor.getBaslamaTarihi()));
            } else {
                pstmt.setNull(11, Types.DATE);
            }

            pstmt.setBoolean(12, doktor.isAktif());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        doktor.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return doktor;

        } catch (SQLException e) {
            System.err.println("Doktor kaydetme hatası: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Doktor update(Doktor doktor) {
        String sql = "UPDATE doktorlar SET doktor_no = ?, tc_kimlik = ?, ad = ?, soyad = ?, uzmanlik = ?, poliklinik_id = ?, unvan = ?, telefon = ?, email = ?, maas = ?, baslama_tarihi = ?, aktif = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doktor.getDoktorNo());
            pstmt.setString(2, doktor.getTcKimlik());
            pstmt.setString(3, doktor.getAd());
            pstmt.setString(4, doktor.getSoyad());
            pstmt.setString(5, doktor.getUzmanlik());
            pstmt.setInt(6, doktor.getPoliklinikId());
            pstmt.setString(7, doktor.getUnvan());
            pstmt.setString(8, doktor.getTelefon());
            pstmt.setString(9, doktor.getEmail());
            pstmt.setDouble(10, doktor.getMaas());

            if (doktor.getBaslamaTarihi() != null) {
                pstmt.setDate(11, Date.valueOf(doktor.getBaslamaTarihi()));
            } else {
                pstmt.setNull(11, Types.DATE);
            }

            pstmt.setBoolean(12, doktor.isAktif());
            pstmt.setInt(13, doktor.getId());

            pstmt.executeUpdate();
            return doktor;

        } catch (SQLException e) {
            System.err.println("Doktor güncelleme hatası: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM doktorlar WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Doktor silme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existsByDoktorNo(String doktorNo) {
        String sql = "SELECT COUNT(*) FROM doktorlar WHERE doktor_no = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doktorNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Doktor no kontrol hatası: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean existsByTcKimlik(String tcKimlik) {
        String sql = "SELECT COUNT(*) FROM doktorlar WHERE tc_kimlik = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tcKimlik);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("TC kimlik kontrol hatası: " + e.getMessage());
        }
        return false;
    }

    @Override
    public int countByPoliklinik(int poliklinikId) {
        String sql = "SELECT COUNT(*) FROM doktorlar WHERE poliklinik_id = ? AND aktif = TRUE";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, poliklinikId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Poliklinik doktor sayısı alma hatası: " + e.getMessage());
        }
        return 0;
    }

    private Doktor mapResultSetToDoktor(ResultSet rs) throws SQLException {
        Doktor doktor = new Doktor();
        doktor.setId(rs.getInt("id"));
        doktor.setDoktorNo(rs.getString("doktor_no"));
        doktor.setTcKimlik(rs.getString("tc_kimlik"));
        doktor.setAd(rs.getString("ad"));
        doktor.setSoyad(rs.getString("soyad"));
        doktor.setUzmanlik(rs.getString("uzmanlik"));
        doktor.setPoliklinikId(rs.getInt("poliklinik_id"));
        doktor.setUnvan(rs.getString("unvan"));
        doktor.setTelefon(rs.getString("telefon"));
        doktor.setEmail(rs.getString("email"));
        doktor.setMaas(rs.getDouble("maas"));

        Date baslamaTarihi = rs.getDate("baslama_tarihi");
        if (baslamaTarihi != null) {
            doktor.setBaslamaTarihi(baslamaTarihi.toLocalDate());
        }

        doktor.setAktif(rs.getBoolean("aktif"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            doktor.setCreatedAt(createdAt.toLocalDateTime());
        }

        return doktor;
    }
}