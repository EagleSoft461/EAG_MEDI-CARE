# 🏥 EAG MEDI-CARE - Hastane Yönetim Sistemi

[![Java](https://img.shields.io/badge/Java-11%2B-blue.svg)](https://java.com)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://mysql.com)
[![Swing](https://img.shields.io/badge/Java%20Swing-GUI-green.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Lisans](https://img.shields.io/badge/Lisans-MIT-yellow.svg)](LICENSE)

<div align="center">

![EAG MEDI-CARE](https://via.placeholder.com/800x400/2c3e50/ffffff?text=EAG+MEDI-CARE+Hastane+Yönetim+Sistemi)

**Modern, verimli ve kullanıcı dostu hastane yönetim çözümü**

</div>

## 📋 İçindekiler

- [✨ Özellikler](#-özellikler)
- [🚀 Hızlı Başlangıç](#-hızlı-başlangıç)
- [🛠️ Kurulum](#️-kurulum)
- [📁 Proje Yapısı](#-proje-yapısı)
- [🎯 Kullanım](#-kullanım)
- [🖼️ Ekran Görüntüleri](#️-ekran-görüntüleri)
- [🔧 Yapılandırma](#-yapılandırma)
- [🤝 Katkıda Bulunma](#-katkıda-bulunma)
- [📄 Lisans](#-lisans)
- [👥 Katkıda Bulunanlar](#-katkıda-bulunanlar)

## ✨ Özellikler

### 🎨 Modern Arayüz
- **Responsive Tasarım** - Modern ve profesyonel arayüz
- **Karanlık/Açık Temalar** - Çoklu tema seçenekleri
- **Gerçek Zamanlı Arama** - Anında filtreleme ve arama
- **Yazdırma Desteği** - Verileri yazdırılabilir formata aktar

### 👨‍⚕️ Doktor Yönetimi
- ✅ Doktor ekleme, düzenleme, silme
- ✅ Uzmanlık ve bölüm yönetimi
- ✅ İletişim bilgileri ve müsaitlik
- ✅ Aktif/pasif durum takibi

### 👥 Hasta Yönetimi
- ✅ Tam hasta profilleri
- ✅ Tıbbi geçmiş kayıtları
- ✅ İletişim ve demografik bilgiler
- ✅ Kayıt tarihi takibi

### 📅 Randevu Sistemi
- ✅ Randevu oluşturma ve yönetme
- ✅ Doktor ve poliklinik seçimi
- ✅ Zaman aralığı yönetimi
- ✅ Randevu durum takibi

### 🏥 Poliklinik Yönetimi
- ✅ Bölüm yönetimi
- ✅ Kat ve konum takibi
- ✅ Doktor atama
- ✅ İletişim bilgileri

### 💊 Reçete Yönetimi
- ✅ Dijital reçete oluşturma
- ✅ İlaç ve dozaj takibi
- ✅ Kullanım talimatları
- ✅ Reçete geçmişi

### 📊 Dashboard & Analitik
- ✅ Gerçek zamanlı istatistikler
- ✅ Görsel veri temsili
- ✅ Sistem genel bakış
- ✅ Performans metrikleri

## 🚀 Hızlı Başlangıç

### Ön Gereksinimler
- **Java JDK 11** veya üstü
- **MySQL Server 8.0** veya üstü
- **MySQL Connector/J**

### 🛠️ Kurulum

1. **Repository'yi klonlayın**
```bash
git clone https://github.com/kullanici-adiniz/eag-medi-care.git
cd eag-medi-care
Veritabanı Kurulumu

sql
-- MySQL otomatik olarak veritabanını ve tabloları oluşturacak
-- MySQL sunucusunun localhost:3306'da çalıştığından emin olun
Yapılandırma
Proje kök dizininde config.properties dosyası oluşturun:

properties
# Veritabanı Yapılandırması
db.url=jdbc:mysql://localhost:3306/
db.name=hastane_randevu_pro
db.username=mysql_kullanici_adiniz
db.password=mysql_sifreniz

# Uygulama Ayarları
app.name=EAG MEDI-CARE
app.version=1.0.0
MySQL Connector Ekleyin

mysql-connector-j-9.4.0.jar indirin

lib/ dizinine yerleştirin

Proje classpath'ine ekleyin

Uygulamayı Çalıştırın

bash
# Derle ve çalıştır
javac -cp "lib/*" src/ui/ModernHospitalUI.java
java -cp "src;lib/*" ui.ModernHospitalUI
📁 Proje Yapısı
text
EAG-MEDI-CARE/
├── 📁 src/
│   ├── 📁 ui/
│   │   └── ModernHospitalUI.java      # Ana uygulama penceresi
│   ├── 📁 data/
│   │   └── DatabaseManager.java       # Veritabanı işlemleri
│   └── 📁 config/
│       └── DatabaseConfig.java        # Veritabanı yapılandırması
├── 📁 util/
│   ├── PrintUtility.java              # Yazdırma fonksiyonelliği
│   └── SearchPanel.java               # Arama ve filtreleme
├── 📁 lib/
│   └── mysql-connector-j-9.4.0.jar    # MySQL sürücüsü
├── 📄 config.properties               # Uygulama yapılandırması
├── 📄 README.md                       # Proje dokümantasyonu
└── 📄 LICENSE                         # MIT Lisansı
🎯 Kullanım
Uygulamayı Başlatma
MySQL sunucusunun çalıştığından emin olun

ModernHospitalUI.java dosyasını çalıştırın

Sistem otomatik olarak veritabanı ve tabloları oluşturur

Modüller arasında gezinmek için yan menüyü kullanın

Modül Kılavuzu
📊 Dashboard
Gerçek zamanlı istatistikleri görüntüleyin

Sistem performansını izleyin

Tüm modüllere hızlı erişim

👨‍⚕️ Doktor Yönetimi
Tam profilli yeni doktorlar ekleyin

Polikliniklere atayın

Müsaitlik ve iletişim bilgilerini yönetin

👥 Hasta Yönetimi
Yeni hastalar kaydedin

Tıbbi kayıtları tutun

Hasta geçmişini takip edin

📅 Randevu Planlama
Müsait doktorlarla randevu oluşturun

Zaman aralıklarını yönetin

Randevu durumunu takip edin

🖼️ Ekran Görüntüleri
<div align="center">
Dashboard	Doktor Yönetimi	Hasta Yönetimi
https://via.placeholder.com/300x200/3498db/ffffff?text=Dashboard	https://via.placeholder.com/300x200/2ecc71/ffffff?text=Doktorlar	https://via.placeholder.com/300x200/e74c3c/ffffff?text=Hastalar
Randevular	Poliklinikler	Reçeteler
https://via.placeholder.com/300x200/9b59b6/ffffff?text=Randevular	https://via.placeholder.com/300x200/f39c12/ffffff?text=Poliklinikler	https://via.placeholder.com/300x200/1abc9c/ffffff?text=Re%C3%A7eteler
</div>
🔧 Yapılandırma
Veritabanı Yapılandırması
Sistem otomatik olarak şunları halleder:

✅ Veritabanı oluşturma

✅ Tablo başlatma

✅ Örnek veri ekleme

✅ Bağlantı yönetimi

Özelleştirme
Şunları özelleştirebilirsiniz:

ModernHospitalUI.java dosyasındaki renk temaları

config.properties dosyasındaki veritabanı ayarları

UI bileşenleri ve düzenleri

DatabaseManager.java dosyasındaki iş mantığı

🤝 Katkıda Bulunma
Katkılarınızı bekliyoruz! Lütfen şu adımları izleyin:

Repository'yi fork edin

Özellik branch'i oluşturun (git checkout -b ozellik/HarikaOzellik)

Değişikliklerinizi commit edin (git commit -m 'Harika bir özellik ekle')

Branch'e push edin (git push origin ozellik/HarikaOzellik)

Pull Request açın

Geliştirme Kuralları
Java kodlama kurallarını takip edin

Anlamlı commit mesajları yazın

Göndermeden önce tüm fonksiyonları test edin

Dokümantasyonu buna göre güncelleyin

📄 Lisans
Bu proje MIT Lisansı altında lisanslanmıştır - detaylar için LİSANS dosyasına bakın.

text
MIT Lisansı

Copyright (c) 2024 EAG MEDI-CARE

Bu dosyanın bir kopyasını ve ilişkili dokümantasyon dosyalarını ("Yazılım") 
edinmek isteyen herkese, yazılımın kullanım, kopyalama, değiştirme, birleştirme, 
yayımlama, dağıtma, alt lisanslama ve/veya yazılımın kopyalarını satma 
hakkı da dahil olmak üzere ve kısıtlama olmaksızın yazılımla deal etmek için 
izin verilir.
👥 Katkıda Bulunanlar
<div align="center">
EAG Takımı tarafından ❤️ ile geliştirilmiştir

https://img.shields.io/badge/Katk%C4%B1da_Bulunan-Ad%C4%B1n%C4%B1z-mavi.svg

</div>
<div align="center">
⭐ GitHub'da Bize Yıldız Verin!
Bu projeyi faydalı bulduysanız, lütfen GitHub'da yıldız verin!

EAG MEDI-CARE - Modern Sağlık Çözümleri

</div>
🚀 Dağıtım Notları
Üretim Kullanımı İçin
config.properties dosyasındaki veritabanı kimlik bilgilerini güncelleyin

MySQL sunucusunu üretim için yapılandırın

Uygun yedekleme stratejileri oluşturun

Gerekirse kullanıcı kimlik doğrulaması uygulayın

Günlük kaydı ve izlemeyi yapılandırın

Güvenlik Değerlendirmeleri
🔒 Güçlü veritabanı şifreleri kullanın

🔒 Girdi doğrulama uygulayın

🔒 Düzenli güvenlik güncellemeleri

🔒 Veritabanı yedekleme rutinleri

<div align="center">
Yardıma mı ihtiyacınız var? Open an issue or contact the development team!

https://img.shields.io/badge/Made%2520with-Java%2520%2526%2520%E2%9D%A4%EF%B8%8F-red.svg
https://img.shields.io/badge/Powered%2520by-MySQL-orange.svg
https://img.shields.io/badge/UI-Java%2520Swing-green.svg

</div>
📞 Destek
For support and questions:

📧 Email: destek@eag-medi-care.com

🐛 Issues: GitHub Issues

💬 Discussions: GitHub Discussions

EAG MEDI-CARE - Hastane yönetimini modern teknolojiyle devrimleştiriyor! 🏥✨

🌟 Proje Hakkında
EAG MEDI-CARE, sağlık kuruluşları için tasarlanmış kapsamlı bir hastane yönetim sistemidir. Sistem, hastane operasyonlarını dijitalleştirerek verimliliği artırmayı ve hasta bakım kalitesini iyileştirmeyi hedefler.

<div align="center">
EAG MEDI-CARE ile hastane yönetimini modernleştirin! 🏥

https://img.shields.io/badge/%F0%9F%9A%80-Hemen_Ba%C5%9Flay%C4%B1n-brightgreen.svg

</div> ```
