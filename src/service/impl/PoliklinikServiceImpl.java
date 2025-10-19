// service/impl/PoliklinikServiceImpl.java
package service.impl;

import service.PoliklinikService;
import repository.PoliklinikRepository;
import repository.impl.PoliklinikRepositoryImpl;
import model.Poliklinik;

import java.util.List;
import java.util.Optional;

public class PoliklinikServiceImpl implements PoliklinikService {

    private final PoliklinikRepository poliklinikRepository;

    public PoliklinikServiceImpl() {
        this.poliklinikRepository = new PoliklinikRepositoryImpl();
    }

    @Override
    public List<Poliklinik> getAllPoliklinikler() {
        return poliklinikRepository.findAll();
    }

    @Override
    public Optional<Poliklinik> getPoliklinikById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Geçersiz poliklinik ID: " + id);
        }
        return poliklinikRepository.findById(id);
    }

    @Override
    public Optional<Poliklinik> getPoliklinikByAd(String ad) {
        if (ad == null || ad.trim().isEmpty()) {
            throw new IllegalArgumentException("Poliklinik adı boş olamaz");
        }
        return poliklinikRepository.findByAd(ad.trim());
    }

    @Override
    public Poliklinik createPoliklinik(Poliklinik poliklinik) {
        // Validasyon
        if (poliklinik == null) {
            throw new IllegalArgumentException("Poliklinik boş olamaz");
        }
        if (poliklinik.getAd() == null || poliklinik.getAd().trim().isEmpty()) {
            throw new IllegalArgumentException("Poliklinik adı boş olamaz");
        }
        if (poliklinikRepository.existsByAd(poliklinik.getAd())) {
            throw new IllegalArgumentException("Bu isimde bir poliklinik zaten mevcut: " + poliklinik.getAd());
        }

        return poliklinikRepository.save(poliklinik);
    }

    @Override
    public Poliklinik updatePoliklinik(Poliklinik poliklinik) {
        // Validasyon
        if (poliklinik == null) {
            throw new IllegalArgumentException("Poliklinik boş olamaz");
        }
        if (poliklinik.getId() <= 0) {
            throw new IllegalArgumentException("Geçersiz poliklinik ID: " + poliklinik.getId());
        }
        if (poliklinik.getAd() == null || poliklinik.getAd().trim().isEmpty()) {
            throw new IllegalArgumentException("Poliklinik adı boş olamaz");
        }

        // Mevcut poliklinik kontrolü
        Optional<Poliklinik> existing = poliklinikRepository.findById(poliklinik.getId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Güncellenecek poliklinik bulunamadı: " + poliklinik.getId());
        }

        // Ad değiştiyse benzersizlik kontrolü
        Poliklinik existingPoliklinik = existing.get();
        if (!existingPoliklinik.getAd().equals(poliklinik.getAd())) {
            if (poliklinikRepository.existsByAd(poliklinik.getAd())) {
                throw new IllegalArgumentException("Bu isimde bir poliklinik zaten mevcut: " + poliklinik.getAd());
            }
        }

        return poliklinikRepository.update(poliklinik);
    }

    @Override
    public boolean deletePoliklinik(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Geçersiz poliklinik ID: " + id);
        }
        return poliklinikRepository.delete(id);
    }

    @Override
    public List<Poliklinik> getAktifPoliklinikler() {
        return poliklinikRepository.findByAktif(true);
    }

    @Override
    public List<Poliklinik> getPolikliniklerByKat(String kat) {
        if (kat == null || kat.trim().isEmpty()) {
            throw new IllegalArgumentException("Kat bilgisi boş olamaz");
        }
        return poliklinikRepository.findByKat(kat.trim());
    }

    @Override
    public int getToplamPoliklinikSayisi() {
        return poliklinikRepository.count();
    }

    @Override
    public boolean isPoliklinikMevcut(String ad) {
        if (ad == null || ad.trim().isEmpty()) {
            return false;
        }
        return poliklinikRepository.existsByAd(ad.trim());
    }

    @Override
    public boolean deaktiveEtPoliklinik(int id) {
        Optional<Poliklinik> poliklinikOpt = getPoliklinikById(id);
        if (poliklinikOpt.isPresent()) {
            Poliklinik poliklinik = poliklinikOpt.get();
            poliklinik.setAktif(false);
            return poliklinikRepository.update(poliklinik) != null;
        }
        return false;
    }

    @Override
    public boolean aktifEtPoliklinik(int id) {
        Optional<Poliklinik> poliklinikOpt = getPoliklinikById(id);
        if (poliklinikOpt.isPresent()) {
            Poliklinik poliklinik = poliklinikOpt.get();
            poliklinik.setAktif(true);
            return poliklinikRepository.update(poliklinik) != null;
        }
        return false;
    }
}
