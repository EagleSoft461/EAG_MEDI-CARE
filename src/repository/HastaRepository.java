// repository/HastaRepository.java
package repository;

import model.Hasta;
import java.util.List;
import java.util.Optional;

public interface HastaRepository {
    List<Hasta> findAll();
    Optional<Hasta> findById(int id);
    Optional<Hasta> findByTcKimlik(String tcKimlik);
    List<Hasta> findByAdSoyad(String ad, String soyad);
    Hasta save(Hasta hasta);
    Hasta update(Hasta hasta);
    boolean delete(int id);
    boolean existsByTcKimlik(String tcKimlik);
    int count();
}