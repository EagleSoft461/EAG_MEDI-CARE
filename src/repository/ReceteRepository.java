// repository/ReceteRepository.java
package repository;

import model.Recete;
import java.util.List;
import java.util.Optional;

public interface ReceteRepository {
    List<Recete> findAll();
    Optional<Recete> findById(int id);
    List<Recete> findByHastaId(int hastaId);
    List<Recete> findByDoktorId(int doktorId);
    List<Recete> findByRandevuId(int randevuId);
    Recete save(Recete recete);
    Recete update(Recete recete);
    boolean delete(int id);
    List<Recete> findByTarihAraligi(java.time.LocalDateTime baslangic, java.time.LocalDateTime bitis);
}