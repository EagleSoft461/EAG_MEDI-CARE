// model/Recete.java
package model;

import java.time.LocalDateTime;

public class Recete {
    private int id;
    private int randevuId;
    private int doktorId;
    private int hastaId;
    private String ilaclar;
    private String dozaj;
    private String kullanimSekli;
    private String notlar;
    private LocalDateTime receteTarihi;
    private LocalDateTime createdAt;

    public Recete() {}

    public Recete(int randevuId, int doktorId, int hastaId) {
        this.randevuId = randevuId;
        this.doktorId = doktorId;
        this.hastaId = hastaId;
        this.receteTarihi = LocalDateTime.now();
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRandevuId() { return randevuId; }
    public void setRandevuId(int randevuId) { this.randevuId = randevuId; }

    public int getDoktorId() { return doktorId; }
    public void setDoktorId(int doktorId) { this.doktorId = doktorId; }

    public int getHastaId() { return hastaId; }
    public void setHastaId(int hastaId) { this.hastaId = hastaId; }

    public String getIlaclar() { return ilaclar; }
    public void setIlaclar(String ilaclar) { this.ilaclar = ilaclar; }

    public String getDozaj() { return dozaj; }
    public void setDozaj(String dozaj) { this.dozaj = dozaj; }

    public String getKullanimSekli() { return kullanimSekli; }
    public void setKullanimSekli(String kullanimSekli) { this.kullanimSekli = kullanimSekli; }

    public String getNotlar() { return notlar; }
    public void setNotlar(String notlar) { this.notlar = notlar; }

    public LocalDateTime getReceteTarihi() { return receteTarihi; }
    public void setReceteTarihi(LocalDateTime receteTarihi) { this.receteTarihi = receteTarihi; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Reçete #" + id + " - " + receteTarihi.toLocalDate();
    }
}