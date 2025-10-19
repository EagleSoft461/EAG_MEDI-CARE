// service/PoliklinikService.java
package service;

import model.Poliklinik;
import java.util.List;
import java.util.Optional;

public interface PoliklinikService {
    // Temel işlemler
    List<Poliklinik> getAllPoliklinikler();
    Optional<Poliklinik> getPoliklinikById(int id);
    Optional<Poliklinik> getPoliklinikByAd(String ad);
    Poliklinik createPoliklinik(Poliklinik poliklinik);
    Poliklinik updatePoliklinik(Poliklinik poliklinik);
    boolean deletePoliklinik(int id);

    // İş mantığı operasyonları
    List<Poliklinik> getAktifPoliklinikler();
    List<Poliklinik> getPolikliniklerByKat(String kat);
    int getToplamPoliklinikSayisi();
    boolean isPoliklinikMevcut(String ad);
    boolean deaktiveEtPoliklinik(int id);
    boolean aktifEtPoliklinik(int id);
}