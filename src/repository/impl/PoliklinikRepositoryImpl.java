// repository/impl/PoliklinikRepositoryImpl.java
package repository.impl;

import repository.PoliklinikRepository;
import model.Poliklinik;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PoliklinikRepositoryImpl implements PoliklinikRepository {

    @Override
    public List<Poliklinik> findAll() {
        List<Poliklinik> poliklinikler = new ArrayList<>();
        String sql = "SELECT * FROM poliklinikler ORDER BY ad";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                poliklinikler.add(mapResultSetToPoliklinik(rs));
            }
        } catch (SQLException e) {
            System.err.println("Poliklinik listeleme hatası: " + e.getMessage());
        }
        return poliklinikler;
    }

    @Override
    public Optional<Poliklinik> findById(int id) {
        String sql = "SELECT * FROM poliklinikler WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToPoliklinik(rs));
            }
        } catch (SQLException e) {
            System.err.println("Poliklinik bulma hatası: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Poliklinik> findByAd(String ad) {
        String sql = "SELECT * FROM poliklinikler WHERE ad = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ad);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToPoliklinik(rs));
            }
        } catch (SQLException e) {
            System.err.println("Poliklinik ad ile bulma hatası: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Poliklinik save(Poliklinik poliklinik) {
        String sql = "INSERT INTO poliklinikler (ad, aciklama, kat, telefon, aktif) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, poliklinik.getAd());
            pstmt.setString(2, poliklinik.getAciklama());
            pstmt.setString(3, poliklinik.getKat());
            pstmt.setString(4, poliklinik.getTelefon());
            pstmt.setBoolean(5, poliklinik.isAktif());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        poliklinik.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return poliklinik;

        } catch (SQLException e) {
            System.err.println("Poliklinik kaydetme hatası: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Poliklinik update(Poliklinik poliklinik) {
        String sql = "UPDATE poliklinikler SET ad = ?, aciklama = ?, kat = ?, telefon = ?, aktif = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, poliklinik.getAd());
            pstmt.setString(2, poliklinik.getAciklama());
            pstmt.setString(3, poliklinik.getKat());
            pstmt.setString(4, poliklinik.getTelefon());
            pstmt.setBoolean(5, poliklinik.isAktif());
            pstmt.setInt(6, poliklinik.getId());

            pstmt.executeUpdate();
            return poliklinik;

        } catch (SQLException e) {
            System.err.println("Poliklinik güncelleme hatası: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM poliklinikler WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Poliklinik silme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Poliklinik> findByAktif(boolean aktif) {
        List<Poliklinik> poliklinikler = new ArrayList<>();
        String sql = "SELECT * FROM poliklinikler WHERE aktif = ? ORDER BY ad";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, aktif);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                poliklinikler.add(mapResultSetToPoliklinik(rs));
            }
        } catch (SQLException e) {
            System.err.println("Aktif poliklinikleri bulma hatası: " + e.getMessage());
        }
        return poliklinikler;
    }

    @Override
    public List<Poliklinik> findByKat(String kat) {
        List<Poliklinik> poliklinikler = new ArrayList<>();
        String sql = "SELECT * FROM poliklinikler WHERE kat = ? ORDER BY ad";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kat);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                poliklinikler.add(mapResultSetToPoliklinik(rs));
            }
        } catch (SQLException e) {
            System.err.println("Kat'a göre poliklinik bulma hatası: " + e.getMessage());
        }
        return poliklinikler;
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM poliklinikler";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Poliklinik sayısı alma hatası: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean existsByAd(String ad) {
        String sql = "SELECT COUNT(*) FROM poliklinikler WHERE ad = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ad);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Poliklinik ad kontrol hatası: " + e.getMessage());
        }
        return false;
    }

    private Poliklinik mapResultSetToPoliklinik(ResultSet rs) throws SQLException {
        Poliklinik poliklinik = new Poliklinik();
        poliklinik.setId(rs.getInt("id"));
        poliklinik.setAd(rs.getString("ad"));
        poliklinik.setAciklama(rs.getString("aciklama"));
        poliklinik.setKat(rs.getString("kat"));
        poliklinik.setTelefon(rs.getString("telefon"));
        poliklinik.setAktif(rs.getBoolean("aktif"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            poliklinik.setCreatedAt(createdAt.toLocalDateTime());
        }

        return poliklinik;
    }
}
