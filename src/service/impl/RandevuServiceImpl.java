// service/impl/RandevuServiceImpl.java
package service.impl;

import service.RandevuService;
import repository.RandevuRepository;
import repository.impl.RandevuRepositoryImpl;
import model.Randevu;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RandevuServiceImpl implements RandevuService {

    private final RandevuRepository randevuRepository;

    public RandevuServiceImpl() {
        this.randevuRepository = new RandevuRepositoryImpl();
    }

    @Override
    public List<Randevu> getAllRandevular() {
        return randevuRepository.findAll();
    }

    @Override
    public Optional<Randevu> getRandevuById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Geçersiz randevu ID: " + id);
        }
        return randevuRepository.findById(id);
    }

    @Override
    public List<Randevu> getRandevularByHasta(int hastaId) {
        if (hastaId <= 0) {
            throw new IllegalArgumentException("Geçersiz hasta ID: " + hastaId);
        }
        return randevuRepository.findByHastaId(hastaId);
    }

    @Override
    public List<Randevu> getRandevularByDoktor(int doktorId) {
        if (doktorId <= 0) {
            throw new IllegalArgumentException("Geçersiz doktor ID: " + doktorId);
        }
        return randevuRepository.findByDoktorId(doktorId);
    }

    @Override
    public List<Randevu> getRandevularByPoliklinik(int poliklinikId) {
        if (poliklinikId <= 0) {
            throw new IllegalArgumentException("Geçersiz poliklinik ID: " + poliklinikId);
        }
        return randevuRepository.findByPoliklinikId(poliklinikId);
    }

    @Override
    public List<Randevu> getRandevularByDurum(String durum) {
        if (durum == null || durum.trim().isEmpty()) {
            throw new IllegalArgumentException("Durum boş olamaz");
        }
        return randevuRepository.findByDurumu(durum.trim());
    }

    @Override
    public List<Randevu> getRandevularByTarihAraligi(LocalDateTime baslangic, LocalDateTime bitis) {
        if (baslangic == null || bitis == null) {
            throw new IllegalArgumentException("Başlangıç ve bitiş tarihi boş olamaz");
        }
        if (baslangic.isAfter(bitis)) {
            throw new IllegalArgumentException("Başlangıç tarihi bitiş tarihinden sonra olamaz");
        }
        return randevuRepository.findByTarihAraligi(baslangic, bitis);
    }

    @Override
    public Randevu createRandevu(Randevu randevu) {
        // Validasyon
        if (randevu == null) {
            throw new IllegalArgumentException("Randevu boş olamaz");
        }
        if (randevu.getHastaId() <= 0) {
            throw new IllegalArgumentException("Geçersiz hasta ID");
        }
        if (randevu.getDoktorId() <= 0) {
            throw new IllegalArgumentException("Geçersiz doktor ID");
        }
        if (randevu.getRandevuTarihi() == null) {
            throw new IllegalArgumentException("Randevu tarihi boş olamaz");
        }
        if (randevu.getRandevuTarihi().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Randevu tarihi geçmiş bir tarih olamaz");
        }

        // Doktor müsaitlik kontrolü
        if (!isDoktorMüsait(randevu.getDoktorId(), randevu.getRandevuTarihi())) {
            throw new IllegalArgumentException("Doktor bu saatte müsait değil");
        }

        return randevuRepository.save(randevu);
    }

    @Override
    public Randevu updateRandevu(Randevu randevu) {
        // Validasyon
        if (randevu == null) {
            throw new IllegalArgumentException("Randevu boş olamaz");
        }
        if (randevu.getId() <= 0) {
            throw new IllegalArgumentException("Geçersiz randevu ID: " + randevu.getId());
        }

        // Mevcut randevu kontrolü
        Optional<Randevu> existing = randevuRepository.findById(randevu.getId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Güncellenecek randevu bulunamadı: " + randevu.getId());
        }

        return randevuRepository.update(randevu);
    }

    @Override
    public boolean deleteRandevu(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Geçersiz randevu ID: " + id);
        }
        return randevuRepository.delete(id);
    }

    @Override
    public boolean onaylaRandevu(int id) {
        Optional<Randevu> randevuOpt = getRandevuById(id);
        if (randevuOpt.isPresent()) {
            Randevu randevu = randevuOpt.get();
            randevu.setDurumu("ONAYLANDI");
            return randevuRepository.update(randevu) != null;
        }
        return false;
    }

    @Override
    public boolean iptalEtRandevu(int id, String aciklama) {
        Optional<Randevu> randevuOpt = getRandevuById(id);
        if (randevuOpt.isPresent()) {
            Randevu randevu = randevuOpt.get();
            randevu.setDurumu("IPTAL");
            randevu.setAciklama(aciklama);
            return randevuRepository.update(randevu) != null;
        }
        return false;
    }

    @Override
    public boolean tamamlaRandevu(int id) {
        Optional<Randevu> randevuOpt = getRandevuById(id);
        if (randevuOpt.isPresent()) {
            Randevu randevu = randevuOpt.get();
            randevu.setDurumu("TAMAMLANDI");
            return randevuRepository.update(randevu) != null;
        }
        return false;
    }

    @Override
    public boolean isDoktorMüsait(int doktorId, LocalDateTime tarih) {
        if (doktorId <= 0) {
            throw new IllegalArgumentException("Geçersiz doktor ID: " + doktorId);
        }
        if (tarih == null) {
            throw new IllegalArgumentException("Tarih boş olamaz");
        }
        return randevuRepository.isDoktorMüsait(doktorId, tarih);
    }

    @Override
    public List<LocalDateTime> getMüsaitRandevuSaatleri(int doktorId, LocalDateTime tarih) {
        return List.of();
    }
}