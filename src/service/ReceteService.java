// service/ReceteService.java
package service;

import model.Recete;
import java.util.List;
import java.util.Optional;

public interface ReceteService {
    List<Recete> getAllReceteler();
    Optional<Recete> getReceteById(int id);
    List<Recete> getRecetelerByHasta(int hastaId);
    List<Recete> getRecetelerByDoktor(int doktorId);
    List<Recete> getRecetelerByRandevu(int randevuId);
    Recete createRecete(Recete recete);
    Recete updateRecete(Recete recete);
    boolean deleteRecete(int id);
    List<Recete> getRecetelerByTarihAraligi(java.time.LocalDateTime baslangic, java.time.LocalDateTime bitis);
    boolean isValidRecete(Recete recete);
}