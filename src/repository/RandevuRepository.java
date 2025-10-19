// repository/RandevuRepository.java
package repository;

import model.Randevu;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RandevuRepository {
    List<Randevu> findAll();
    Optional<Randevu> findById(int id);
    List<Randevu> findByHastaId(int hastaId);
    List<Randevu> findByDoktorId(int doktorId);
    List<Randevu> findByPoliklinikId(int poliklinikId);
    List<Randevu> findByDurumu(String durumu);
    List<Randevu> findByTarihAraligi(LocalDateTime baslangic, LocalDateTime bitis);
    boolean isDoktorMüsait(int doktorId, LocalDateTime tarih);
    Randevu save(Randevu randevu);
    Randevu update(Randevu randevu);
    boolean delete(int id);

    List<LocalDateTime> getMüsaitRandevuSaatleri(int doktorId, LocalDateTime tarih);

    int countByDoktorAndDurum(int doktorId, String durum);
}