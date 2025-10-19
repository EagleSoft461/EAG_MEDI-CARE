// repository/PoliklinikRepository.java
package repository;

import model.Poliklinik;
import java.util.List;
import java.util.Optional;

public interface PoliklinikRepository {
    // Temel CRUD operasyonları
    List<Poliklinik> findAll();
    Optional<Poliklinik> findById(int id);
    Optional<Poliklinik> findByAd(String ad);
    Poliklinik save(Poliklinik poliklinik);
    Poliklinik update(Poliklinik poliklinik);
    boolean delete(int id);

    // Özel sorgular
    List<Poliklinik> findByAktif(boolean aktif);
    List<Poliklinik> findByKat(String kat);
    int count();
    boolean existsByAd(String ad);
}