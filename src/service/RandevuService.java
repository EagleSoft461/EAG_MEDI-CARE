// service/RandevuService.java
package service;

import model.Randevu;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RandevuService {
    List<Randevu> getAllRandevular();
    Optional<Randevu> getRandevuById(int id);
    List<Randevu> getRandevularByHasta(int hastaId);
    List<Randevu> getRandevularByDoktor(int doktorId);
    List<Randevu> getRandevularByPoliklinik(int poliklinikId);
    List<Randevu> getRandevularByDurum(String durum);
    List<Randevu> getRandevularByTarihAraligi(LocalDateTime baslangic, LocalDateTime bitis);
    Randevu createRandevu(Randevu randevu);
    Randevu updateRandevu(Randevu randevu);
    boolean deleteRandevu(int id);
    boolean onaylaRandevu(int id);
    boolean iptalEtRandevu(int id, String aciklama);
    boolean tamamlaRandevu(int id);
    boolean isDoktorMüsait(int doktorId, LocalDateTime tarih);
    List<LocalDateTime> getMüsaitRandevuSaatleri(int doktorId, LocalDateTime tarih);
}