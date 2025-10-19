// model/Doktor.java
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Doktor {
    private int id;
    private String doktorNo;
    private String tcKimlik;
    private String ad;
    private String soyad;
    private String uzmanlik;
    private int poliklinikId;
    private String unvan;
    private String telefon;
    private String email;
    private double maas;
    private LocalDate baslamaTarihi;
    private boolean aktif;
    private LocalDateTime createdAt;

    // Parametresiz constructor (ZORUNLU)
    public Doktor() {
        this.aktif = true;
    }

    // Parametreli constructor
    public Doktor(String doktorNo, String ad, String soyad, String uzmanlik, int poliklinikId) {
        this(); // Parametresiz constructor'ı çağır
        this.doktorNo = doktorNo;
        this.ad = ad;
        this.soyad = soyad;
        this.uzmanlik = uzmanlik;
        this.poliklinikId = poliklinikId;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDoktorNo() { return doktorNo; }
    public void setDoktorNo(String doktorNo) { this.doktorNo = doktorNo; }

    public String getTcKimlik() { return tcKimlik; }
    public void setTcKimlik(String tcKimlik) { this.tcKimlik = tcKimlik; }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }

    public String getUzmanlik() { return uzmanlik; }
    public void setUzmanlik(String uzmanlik) { this.uzmanlik = uzmanlik; }

    public int getPoliklinikId() { return poliklinikId; }
    public void setPoliklinikId(int poliklinikId) { this.poliklinikId = poliklinikId; }

    public String getUnvan() { return unvan; }
    public void setUnvan(String unvan) { this.unvan = unvan; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getMaas() { return maas; }
    public void setMaas(double maas) { this.maas = maas; }

    public LocalDate getBaslamaTarihi() { return baslamaTarihi; }
    public void setBaslamaTarihi(LocalDate baslamaTarihi) { this.baslamaTarihi = baslamaTarihi; }

    public boolean isAktif() { return aktif; }
    public void setAktif(boolean aktif) { this.aktif = aktif; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return (unvan != null ? unvan + " " : "") + ad + " " + soyad + " - " + uzmanlik;
    }
}