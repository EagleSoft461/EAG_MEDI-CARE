// service/TestPoliklinikService.java
package service;

import service.PoliklinikService;
import service.impl.PoliklinikServiceImpl;
import model.Poliklinik;

public class TestPoliklinikService {
    public static void main(String[] args) {
        PoliklinikService poliklinikService = new PoliklinikServiceImpl();

        // Tüm poliklinikleri listele
        System.out.println("=== TÜM POLİKLİNİKLER ===");
        poliklinikService.getAllPoliklinikler().forEach(p ->
                System.out.println(p.getId() + " - " + p.getAd() + " - " + p.getKat())
        );

        // Yeni poliklinik ekle
        System.out.println("\n=== YENİ POLİKLİNİK EKLE ===");
        Poliklinik yeniPoliklinik = new Poliklinik();
        yeniPoliklinik.setAd("Ortopedi");
        yeniPoliklinik.setAciklama("Kemik ve eklem hastalıkları");
        yeniPoliklinik.setKat("4. Kat");
        yeniPoliklinik.setTelefon("0212 444 4444");

        Poliklinik kaydedilen = poliklinikService.createPoliklinik(yeniPoliklinik);
        System.out.println("Yeni poliklinik eklendi: " + kaydedilen.getAd() + " (ID: " + kaydedilen.getId() + ")");

        // Aktif poliklinikleri listele
        System.out.println("\n=== AKTİF POLİKLİNİKLER ===");
        poliklinikService.getAktifPoliklinikler().forEach(p ->
                System.out.println(p.getAd() + " - " + p.getKat())
        );

        // Toplam sayı
        System.out.println("\nToplam poliklinik sayısı: " + poliklinikService.getToplamPoliklinikSayisi());
    }
}
