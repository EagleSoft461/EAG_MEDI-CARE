// model/Poliklinik.java
package model;

import java.time.LocalDateTime;

public class Poliklinik {
    private int id;
    private String ad;
    private String aciklama;
    private String kat;
    private String telefon;
    private boolean aktif;
    private LocalDateTime createdAt;

    // Parametresiz constructor (ZORUNLU)
    public Poliklinik() {
        this.aktif = true;
    }

    // Parametreli constructor
    public Poliklinik(String ad, String aciklama, String kat, String telefon) {
        this(); // Parametresiz constructor'ı çağır
        this.ad = ad;
        this.aciklama = aciklama;
        this.kat = kat;
        this.telefon = telefon;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }

    public String getKat() { return kat; }
    public void setKat(String kat) { this.kat = kat; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public boolean isAktif() { return aktif; }
    public void setAktif(boolean aktif) { this.aktif = aktif; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return ad + " (" + kat + ")";
    }

    public void setPoliklinikAdi(String poliklinikAdi) {
    }

    public Object getPoliklinikAdi() {
        return null;
    }

    public int getPoliklinikId() {
        return 0;
    }
}