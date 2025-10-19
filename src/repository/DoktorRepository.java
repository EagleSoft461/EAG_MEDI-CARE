// repository/DoktorRepository.java
package repository;

import model.Doktor;
import java.util.List;
import java.util.Optional;

public interface DoktorRepository {
    List<Doktor> findAll();
    Optional<Doktor> findById(int id);
    Optional<Doktor> findByDoktorNo(String doktorNo);
    List<Doktor> findByPoliklinikId(int poliklinikId);
    List<Doktor> findByAktif(boolean aktif);
    Doktor save(Doktor doktor);
    Doktor update(Doktor doktor);
    boolean delete(int id);
    boolean existsByDoktorNo(String doktorNo);
    boolean existsByTcKimlik(String tcKimlik);
    int countByPoliklinik(int poliklinikId);
}