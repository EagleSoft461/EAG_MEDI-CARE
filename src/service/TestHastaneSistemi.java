// service/TestHastaneSistemi.java
package service;

import service.impl.DoktorServiceImpl;
import service.impl.RandevuServiceImpl;
import service.impl.ReceteServiceImpl;
import model.*;
import java.time.LocalDateTime;

public class TestHastaneSistemi {
    public static void main(String[] args) {
        testDoktorSistemi();
        testRandevuSistemi();
        // testReceteSistemi(); // Şimdilik comment yap
    }

    private static void testDoktorSistemi() {
        System.out.println("=== DOKTOR SİSTEMİ TESTİ ===");
        DoktorServiceImpl doktorService = new DoktorServiceImpl();

        // Tüm doktorları listele
        System.out.println("Tüm Doktorlar:");
        doktorService.getAllDoktorlar().forEach(d ->
                System.out.println("  " + d.getDoktorNo() + " - " + d.getAd() + " " + d.getSoyad() + " - " + d.getUzmanlik())
        );

        // Yeni doktor ekle
        System.out.println("\nYeni Doktor Ekleme:");
        Doktor yeniDoktor = new Doktor();
        yeniDoktor.setDoktorNo("DOC004");
        yeniDoktor.setAd("Zeynep");
        yeniDoktor.setSoyad("Şahin");
        yeniDoktor.setUzmanlik("Pediatri");
        yeniDoktor.setPoliklinikId(1);
        yeniDoktor.setUnvan("Uzm. Dr.");
        yeniDoktor.setTelefon("0555 444 4444");
        yeniDoktor.setEmail("zeynep.sahin@hastane.com");

        Doktor kaydedilenDoktor = doktorService.createDoktor(yeniDoktor);
        if (kaydedilenDoktor != null) {
            System.out.println("✅ Yeni doktor eklendi: " + kaydedilenDoktor.getAd() + " " + kaydedilenDoktor.getSoyad() + " (ID: " + kaydedilenDoktor.getId() + ")");
        }
    }

    private static void testRandevuSistemi() {
        System.out.println("\n=== RANDEVU SİSTEMİ TESTİ ===");
        RandevuServiceImpl randevuService = new RandevuServiceImpl();

        // Mevcut randevuları listele
        System.out.println("Mevcut Randevular:");
        randevuService.getAllRandevular().forEach(r ->
                System.out.println("  Randevu #" + r.getId() + " - " + r.getRandevuTarihi() + " - " + r.getDurumu())
        );

        // Yeni randevu oluştur
        System.out.println("\nYeni Randevu Oluşturma:");
        Randevu yeniRandevu = new Randevu(1, 1, 1, LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
        yeniRandevu.setAciklama("Kontrol muayenesi");

        Randevu kaydedilenRandevu = randevuService.createRandevu(yeniRandevu);
        if (kaydedilenRandevu != null) {
            System.out.println("✅ Yeni randevu oluşturuldu: " + kaydedilenRandevu.getRandevuTarihi() + " (ID: " + kaydedilenRandevu.getId() + ")");
        }

        // Doktor müsaitlik kontrolü
        System.out.println("\nDoktor Müsaitlik Kontrolü:");
        boolean müsait = randevuService.isDoktorMüsait(1, LocalDateTime.now().plusDays(1).withHour(11).withMinute(0));
        System.out.println("Doktor 1, yarın saat 11:00'de müsait mi? " + (müsait ? "Evet" : "Hayır"));

        // Beklemede olan randevular
        System.out.println("\nBeklemede Olan Randevular:");
        randevuService.getRandevularByDurum("BEKLEMEDE").forEach(r ->
                System.out.println("  Randevu #" + r.getId() + " - " + r.getRandevuTarihi())
        );
    }

    private static void testReceteSistemi() {
        System.out.println("\n=== REÇETE SİSTEMİ TESTİ ===");
        ReceteServiceImpl receteService = new ReceteServiceImpl();

        // Reçeteleri listele
        System.out.println("Mevcut Reçeteler:");
        receteService.getAllReceteler().forEach(r ->
                System.out.println("  Reçete #" + r.getId() + " - " + r.getIlaclar())
        );
    }
}