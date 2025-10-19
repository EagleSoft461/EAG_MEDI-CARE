package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Hasta {
    private int id;
    private String tcKimlik;
    private String name;
    private String surname;
    private LocalDate Dateofbirth;
    private String Gender;
    private String telefon;
    private String email;
    private String adres;
    private LocalDateTime createdAt;

    public Hasta() {}

    public Hasta(String tcKimlik, String ad, String soyad) {
        this.tcKimlik = tcKimlik;
        this.name = ad;
        this.surname = soyad;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTcKimlik() { return tcKimlik; }
    public void setTcKimlik(String tcKimlik) { this.tcKimlik = tcKimlik; }

    public String getAd() { return name; }
    public void setAd(String ad) { this.name = ad; }

    public String getSoyad() { return surname; }
    public void setSoyad(String soyad) { this.surname = soyad; }

    public LocalDate getDogumTarihi() { return Dateofbirth; }
    public void setDogumTarihi(LocalDate dogumTarihi) { this.Dateofbirth = dogumTarihi; }

    public String getCinsiyet() { return Gender; }
    public void setCinsiyet(String cinsiyet) { this.Gender = cinsiyet; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAdres() { return adres; }
    public void setAdres(String adres) { this.adres = adres; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return name + " " + surname + " (" + tcKimlik + ")";
    }
}