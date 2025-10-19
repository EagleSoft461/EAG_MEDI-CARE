package model;

import java.time.LocalDateTime;

public class Randevu {
    private int id;
    private int hastaId;
    private int doktorId;
    private int poliklinikId;
    private LocalDateTime randevuTarihi;
    private String durumu; // BEKLEMEDE, ONAYLANDI, IPTAL, TAMAMLANDI
    private String aciklama;
    private LocalDateTime createdAt;

    public Randevu() {}

    public Randevu(int hastaId, int doktorId, int poliklinikId, LocalDateTime randevuTarihi) {
        this.hastaId = hastaId;
        this.doktorId = doktorId;
        this.poliklinikId = poliklinikId;
        this.randevuTarihi = randevuTarihi;
        this.durumu = "BEKLEMEDE";
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHastaId() { return hastaId; }
    public void setHastaId(int hastaId) { this.hastaId = hastaId; }

    public int getDoktorId() { return doktorId; }
    public void setDoktorId(int doktorId) { this.doktorId = doktorId; }

    public int getPoliklinikId() { return poliklinikId; }
    public void setPoliklinikId(int poliklinikId) { this.poliklinikId = poliklinikId; }

    public LocalDateTime getRandevuTarihi() { return randevuTarihi; }
    public void setRandevuTarihi(LocalDateTime randevuTarihi) { this.randevuTarihi = randevuTarihi; }

    public String getDurumu() { return durumu; }
    public void setDurumu(String durumu) { this.durumu = durumu; }

    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Randevu #" + id + " - " + randevuTarihi + " (" + durumu + ")";
    }
}
