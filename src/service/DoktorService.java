// service/DoktorService.java
package service;

import model.Doktor;
import java.util.List;
import java.util.Optional;

public interface DoktorService {
    List<Doktor> getAllDoktorlar();
    Optional<Doktor> getDoktorById(int id);
    Optional<Doktor> getDoktorByNo(String doktorNo);
    List<Doktor> getDoktorlarByPoliklinik(int poliklinikId);
    List<Doktor> getAktifDoktorlar();
    Doktor createDoktor(Doktor doktor);
    Doktor updateDoktor(Doktor doktor);
    boolean deleteDoktor(int id);
    boolean deaktiveEtDoktor(int id);
    boolean aktifEtDoktor(int id);
    boolean isDoktorNoMevcut(String doktorNo);
    boolean isTcKimlikMevcut(String tcKimlik);
    int getDoktorSayisiByPoliklinik(int poliklinikId);
}