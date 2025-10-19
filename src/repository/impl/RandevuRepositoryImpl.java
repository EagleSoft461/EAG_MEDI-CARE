package repository.impl;

import model.Randevu;
import repository.RandevuRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RandevuRepositoryImpl implements RandevuRepository {

    private final List<Randevu> randevular = new ArrayList<>();

    @Override
    public List<Randevu> findAll() {
        return new ArrayList<>(randevular);
    }

    @Override
    public Optional<Randevu> findById(int id) {
        return randevular.stream()
                .filter(r -> r.getId() == id)
                .findFirst();
    }

    @Override
    public List<Randevu> findByHastaId(int hastaId) {
        List<Randevu> result = new ArrayList<>();
        for (Randevu r : randevular) {
            if (r.getHastaId() == hastaId) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public List<Randevu> findByDoktorId(int doktorId) {
        List<Randevu> result = new ArrayList<>();
        for (Randevu r : randevular) {
            if (r.getDoktorId() == doktorId) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public List<Randevu> findByPoliklinikId(int poliklinikId) {
        List<Randevu> result = new ArrayList<>();
        for (Randevu r : randevular) {
            if (r.getPoliklinikId() == poliklinikId) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public List<Randevu> findByDurumu(String durum) {
        List<Randevu> result = new ArrayList<>();
        for (Randevu r : randevular) {
            if (r.getDurumu().equalsIgnoreCase(durum)) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public List<Randevu> findByTarihAraligi(LocalDateTime baslangic, LocalDateTime bitis) {
        List<Randevu> result = new ArrayList<>();
        for (Randevu r : randevular) {
            if ((r.getRandevuTarihi().isEqual(baslangic) || r.getRandevuTarihi().isAfter(baslangic))
                    && (r.getRandevuTarihi().isEqual(bitis) || r.getRandevuTarihi().isBefore(bitis))) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public Randevu save(Randevu randevu) {
        randevular.add(randevu);
        return randevu;
    }

    @Override
    public Randevu update(Randevu randevu) {
        Optional<Randevu> existingOpt = findById(randevu.getId());
        if (existingOpt.isPresent()) {
            Randevu existing = existingOpt.get();
            randevular.remove(existing);
            randevular.add(randevu);
            return randevu;
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        Optional<Randevu> existingOpt = findById(id);
        if (existingOpt.isPresent()) {
            randevular.remove(existingOpt.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean isDoktorMüsait(int doktorId, LocalDateTime tarih) {
        for (Randevu r : randevular) {
            if (r.getDoktorId() == doktorId && r.getRandevuTarihi().isEqual(tarih)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<LocalDateTime> getMüsaitRandevuSaatleri(int doktorId, LocalDateTime tarih) {
        // Basit örnek: 9:00 - 17:00 her saat başı müsait saatler
        List<LocalDateTime> saatler = new ArrayList<>();
        for (int i = 9; i <= 17; i++) {
            LocalDateTime dt = tarih.withHour(i).withMinute(0).withSecond(0).withNano(0);
            if (isDoktorMüsait(doktorId, dt)) {
                saatler.add(dt);
            }
        }
        return saatler;
    }

    @Override
    public int countByDoktorAndDurum(int doktorId, String durum) {
        int count = 0;
        for (Randevu r : randevular) {
            if (r.getDoktorId() == doktorId && r.getDurumu().equalsIgnoreCase(durum)) {
                count++;
            }
        }
        return count;
    }
}
