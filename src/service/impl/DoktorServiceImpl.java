// service/impl/DoktorServiceImpl.java
package service.impl;

import service.DoktorService;
import repository.DoktorRepository;
import repository.impl.DoktorRepositoryImpl;
import model.Doktor;

import java.util.List;
import java.util.Optional;

public class DoktorServiceImpl implements DoktorService {

    private final DoktorRepository doktorRepository;

    public DoktorServiceImpl() {
        this.doktorRepository = new DoktorRepositoryImpl();
    }

    @Override
    public List<Doktor> getAllDoktorlar() {
        return doktorRepository.findAll();
    }

    @Override
    public Optional<Doktor> getDoktorById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Geçersiz doktor ID: " + id);
        }
        return doktorRepository.findById(id);
    }

    @Override
    public Optional<Doktor> getDoktorByNo(String doktorNo) {
        if (doktorNo == null || doktorNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Doktor no boş olamaz");
        }
        return doktorRepository.findByDoktorNo(doktorNo.trim());
    }

    @Override
    public List<Doktor> getDoktorlarByPoliklinik(int poliklinikId) {
        if (poliklinikId <= 0) {
            throw new IllegalArgumentException("Geçersiz poliklinik ID: " + poliklinikId);
        }
        return doktorRepository.findByPoliklinikId(poliklinikId);
    }

    @Override
    public List<Doktor> getAktifDoktorlar() {
        return doktorRepository.findByAktif(true);
    }

    @Override
    public Doktor createDoktor(Doktor doktor) {
        // Validasyon
        if (doktor == null) {
            throw new IllegalArgumentException("Doktor boş olamaz");
        }
        if (doktor.getDoktorNo() == null || doktor.getDoktorNo().trim().isEmpty()) {
            throw new IllegalArgumentException("Doktor no boş olamaz");
        }
        if (doktor.getAd() == null || doktor.getAd().trim().isEmpty()) {
            throw new IllegalArgumentException("Doktor adı boş olamaz");
        }
        if (doktor.getSoyad() == null || doktor.getSoyad().trim().isEmpty()) {
            throw new IllegalArgumentException("Doktor soyadı boş olamaz");
        }

        // Benzersizlik kontrolleri
        if (doktorRepository.existsByDoktorNo(doktor.getDoktorNo())) {
            throw new IllegalArgumentException("Bu doktor no zaten mevcut: " + doktor.getDoktorNo());
        }
        if (doktor.getTcKimlik() != null && !doktor.getTcKimlik().trim().isEmpty()) {
            if (doktorRepository.existsByTcKimlik(doktor.getTcKimlik())) {
                throw new IllegalArgumentException("Bu TC kimlik no zaten mevcut: " + doktor.getTcKimlik());
            }
        }

        return doktorRepository.save(doktor);
    }

    @Override
    public Doktor updateDoktor(Doktor doktor) {
        // Validasyon
        if (doktor == null) {
            throw new IllegalArgumentException("Doktor boş olamaz");
        }
        if (doktor.getId() <= 0) {
            throw new IllegalArgumentException("Geçersiz doktor ID: " + doktor.getId());
        }
        if (doktor.getDoktorNo() == null || doktor.getDoktorNo().trim().isEmpty()) {
            throw new IllegalArgumentException("Doktor no boş olamaz");
        }

        // Mevcut doktor kontrolü
        Optional<Doktor> existing = doktorRepository.findById(doktor.getId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Güncellenecek doktor bulunamadı: " + doktor.getId());
        }

        // Benzersizlik kontrolleri
        Doktor existingDoktor = existing.get();
        if (!existingDoktor.getDoktorNo().equals(doktor.getDoktorNo())) {
            if (doktorRepository.existsByDoktorNo(doktor.getDoktorNo())) {
                throw new IllegalArgumentException("Bu doktor no zaten mevcut: " + doktor.getDoktorNo());
            }
        }
        if (doktor.getTcKimlik() != null && !doktor.getTcKimlik().trim().isEmpty()) {
            if (!existingDoktor.getTcKimlik().equals(doktor.getTcKimlik())) {
                if (doktorRepository.existsByTcKimlik(doktor.getTcKimlik())) {
                    throw new IllegalArgumentException("Bu TC kimlik no zaten mevcut: " + doktor.getTcKimlik());
                }
            }
        }

        return doktorRepository.update(doktor);
    }

    @Override
    public boolean deleteDoktor(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Geçersiz doktor ID: " + id);
        }
        return doktorRepository.delete(id);
    }

    @Override
    public boolean deaktiveEtDoktor(int id) {
        Optional<Doktor> doktorOpt = getDoktorById(id);
        if (doktorOpt.isPresent()) {
            Doktor doktor = doktorOpt.get();
            doktor.setAktif(false);
            return doktorRepository.update(doktor) != null;
        }
        return false;
    }

    @Override
    public boolean aktifEtDoktor(int id) {
        Optional<Doktor> doktorOpt = getDoktorById(id);
        if (doktorOpt.isPresent()) {
            Doktor doktor = doktorOpt.get();
            doktor.setAktif(true);
            return doktorRepository.update(doktor) != null;
        }
        return false;
    }

    @Override
    public boolean isDoktorNoMevcut(String doktorNo) {
        if (doktorNo == null || doktorNo.trim().isEmpty()) {
            return false;
        }
        return doktorRepository.existsByDoktorNo(doktorNo.trim());
    }

    @Override
    public boolean isTcKimlikMevcut(String tcKimlik) {
        if (tcKimlik == null || tcKimlik.trim().isEmpty()) {
            return false;
        }
        return doktorRepository.existsByTcKimlik(tcKimlik.trim());
    }

    @Override
    public int getDoktorSayisiByPoliklinik(int poliklinikId) {
        if (poliklinikId <= 0) {
            throw new IllegalArgumentException("Geçersiz poliklinik ID: " + poliklinikId);
        }
        return doktorRepository.countByPoliklinik(poliklinikId);
    }
}