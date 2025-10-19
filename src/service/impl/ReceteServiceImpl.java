// service/impl/ReceteServiceImpl.java
package service.impl;

import service.ReceteService;
import model.Recete;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceteServiceImpl implements ReceteService {

    public ReceteServiceImpl() {
        System.out.println("⚠️ ReceteService henüz tam implemente edilmedi");
    }

    @Override
    public List<Recete> getAllReceteler() {
        System.out.println("⚠️ Reçete listeleme henüz implemente edilmedi");
        return new ArrayList<>();
    }

    @Override
    public Optional<Recete> getReceteById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Recete> getRecetelerByHasta(int hastaId) {
        return new ArrayList<>();
    }

    @Override
    public List<Recete> getRecetelerByDoktor(int doktorId) {
        return new ArrayList<>();
    }

    @Override
    public List<Recete> getRecetelerByRandevu(int randevuId) {
        return new ArrayList<>();
    }

    @Override
    public Recete createRecete(Recete recete) {
        System.out.println("⚠️ Reçete oluşturma henüz implemente edilmedi");
        return null;
    }

    @Override
    public Recete updateRecete(Recete recete) {
        return null;
    }

    @Override
    public boolean deleteRecete(int id) {
        return false;
    }

    @Override
    public List<Recete> getRecetelerByTarihAraligi(java.time.LocalDateTime baslangic, java.time.LocalDateTime bitis) {
        return new ArrayList<>();
    }

    @Override
    public boolean isValidRecete(Recete recete) {
        return false;
    }
}