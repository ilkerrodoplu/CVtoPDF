# 📜CVtoPDF
CVtoPDF, kullanıcıdan kişisel bilgiler, eğitim, iş deneyimleri, yetenekler ve dil bilgilerini alarak bunları şık bir PDF özgeçmiş dosyasına dönüştüren bir Java konsol uygulamasıdır.
Uygulama, kullanıcının isteğe bağlı olarak profil fotoğrafını da PDF'e ekler.
## Özellikler
* Kişisel bilgi girişi.
* PDF çıktısı üretimi (CV_Ad_Soyad.pdf şeklinde kaydedilir).
* Profil fotoğrafı ekleme (Zorunlu Değil).
* Türkçe dil desteği.
## Kullanılan Araçlar
* Apache PDFBox Kütüphanesi
## Gereksinimler
* Java Development Kit (JDK) 8 veya üzeri
* Apache PDFBox kütüphanesi
* Arial.ttf font dosyası (opsiyonel, yoksa LiberationSans kullanılır)
## PDFBox Kütüphanesini Ekleme
Aşağıdaki jar dosyalarını bilgisayarınıza indiriniz.
* [pdfbox-2.0.27.jar](https://repo1.maven.org/maven2/org/apache/pdfbox/pdfbox/2.0.27/pdfbox-2.0.27.jar)
* [fontbox-2.0.27.jar](https://repo1.maven.org/maven2/org/apache/pdfbox/fontbox/2.0.27/fontbox-2.0.27.jar)
* [commons-logging-1.2.jar ](https://repo1.maven.org/maven2/commons-logging/commons-logging/1.2/commons-logging-1.2.jar)

JAR dosyalarını projenize eklemek için IDE'nize göre talimatlar verilmiştir:
### IntelliJ IDEA:

* ``File`` → ``Project Structure`` → ``Modules``(Kısaca CTRL + ALT + SHIFT +S)
* ``Libraries`` sekmesine gidin " + " butonuna tıklayın ve "JAVA" seçiniz
* İndirdiğiniz 3 JAR dosyasını seçin
* ``Apply`` →`` OK``

### Eclipse:

* Proje üzerinde sağ tık → ``Properties``
* ``Java Build Path`` → ``Libraries sekmesi``
* ``Add External JARs`` butonuna tıklayın
* İndirdiğiniz 3 JAR dosyasını seçin
* ``Apply`` ve`` Close``

## Program Aşamaları:

Program sırasıyla aşağıdaki bilgileri isteyecektir:

#### 1. **Kişisel Bilgiler**
   - Ad Soyad
   - Telefon
   - E-posta
   - Adres
   - Doğum Tarihi (ör: `15.05.1990` veya `15 Mayıs 1990`)

#### 2. **Özgeçmiş**
   - Kısa özgeçmiş metni

#### 3. **Eğitim Bilgileri**
   - Okul Adı
   - Bölüm
   - Başlangıç ve Bitiş Yılı

#### 4. **İş Deneyimi**
   - Şirket Adı
   - Şirketteki Pozisyonu
   - Başlangıç ve Bitiş Yılı
   - Görevin Tanımı

#### 5. **Yetenekler**
   - Teknik ve kişisel yetenekler

#### 6. **Dil**
   - Dil Adı
   - Seviye (ör: İleri, Orta, Başlangıç)

#### 7. **Profil Fotoğrafı** (opsiyonel)
   - Profil fotoğrafı dosya yolunu giriniz
   - **Örnek:** `C:\Users\hp\Desktop\Fotolar\ProfilFoto.png`
   - Fotoğraf eklemek istemiyorsanız Enter'a basın

### Çıktı:
Program çalıştıktan sonra **`CV_AD_SOYAD.pdf`** formatında profesyonel bir CV dosyası oluşturulur.
