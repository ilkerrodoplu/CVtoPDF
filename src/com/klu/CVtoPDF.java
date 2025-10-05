package com.klu;

import java.util.*;

public class CVtoPDF {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== KİŞİSEL BİLGİLER ===");
        System.out.print("Ad Soyad: ");
        String adSoyad = scanner.nextLine();

        System.out.print("Telefon: ");
        String telefon = scanner.nextLine();

        System.out.print("E-posta: ");
        String eposta = scanner.nextLine();

        System.out.print("Adres: ");
        String adres = scanner.nextLine();

        System.out.print("Doğum Tarihi: ");
        String dogumTarihiGirdi = scanner.nextLine();
        DogumTarihi dogumTarihi = new DogumTarihi(dogumTarihiGirdi);

        System.out.println("\n=== ÖZGEÇMİŞ ===");
        System.out.print("Kısa özgeçminizi yazın: ");
        String ozet = scanner.nextLine();

        System.out.println("\n=== EĞİTİM BİLGİLERİ ===");
        System.out.print("Kaç eğitim bilgisi gireceksiniz? ");
        int egitimSayisi = scanner.nextInt();
        scanner.nextLine();

        List<Egitim> egitimler = new ArrayList<>();
        for (int i = 0; i < egitimSayisi; i++) {
            System.out.println("\nEğitim " + (i + 1) + ":");
            System.out.print("Okul Adı: ");
            String okul = scanner.nextLine();
            System.out.print("Bölüm: ");
            String bolum = scanner.nextLine();
            System.out.print("Başlangıç Yılı (örn: 2015): ");
            String baslangic = scanner.nextLine();
            System.out.print("Bitiş Yılı (örn: 2019 veya 'Devam Ediyor'): ");
            String bitis = scanner.nextLine();
            egitimler.add(new Egitim(okul, bolum, baslangic, bitis));
        }

        System.out.println("\n=== İŞ DENEYİMLERİ ===");
        System.out.print("Kaç iş deneyimi gireceksiniz? ");
        int deneyimSayisi = scanner.nextInt();
        scanner.nextLine();

        List<IsDeneyimi> deneyimler = new ArrayList<>();
        for (int i = 0; i < deneyimSayisi; i++) {
            System.out.println("\nDeneyim " + (i + 1) + ":");
            System.out.print("Şirket Adı: ");
            String sirket = scanner.nextLine();
            System.out.print("Pozisyon: ");
            String pozisyon = scanner.nextLine();
            System.out.print("Başlangıç Yılı (örn: 2019): ");
            String baslangic = scanner.nextLine();
            System.out.print("Bitiş Yılı (örn: 2022 veya 'Devam Ediyor'): ");
            String bitis = scanner.nextLine();
            System.out.print("Görev Tanımı: ");
            String gorev = scanner.nextLine();
            deneyimler.add(new IsDeneyimi(sirket, pozisyon, baslangic, bitis, gorev));
        }

        System.out.println("\n=== YETENEKLER ===");
        System.out.print("Kaç yetenek gireceksiniz? ");
        int yetenekSayisi = scanner.nextInt();
        scanner.nextLine();

        List<String> yetenekler = new ArrayList<>();
        for (int i = 0; i < yetenekSayisi; i++) {
            System.out.print("Yetenek " + (i + 1) + ": ");
            yetenekler.add(scanner.nextLine());
        }

        System.out.println("\n=== DİLLER ===");
        System.out.print("Kaç dil bilgisi gireceksiniz? ");
        int dilSayisi = scanner.nextInt();
        scanner.nextLine();

        List<Dil> diller = new ArrayList<>();
        for (int i = 0; i < dilSayisi; i++) {
            System.out.print("Dil " + (i + 1) + ": ");
            String dilAdi = scanner.nextLine();
            System.out.print("Seviye (örn: İleri, Orta): ");
            String seviye = scanner.nextLine();
            diller.add(new Dil(dilAdi, seviye));
        }

        System.out.print("\nProfil fotoğrafı yolu (veya Enter'a basın): ");
        String fotoYolu = scanner.nextLine().trim();

        scanner.close();

        pdfOlustur(adSoyad, telefon, eposta, adres, dogumTarihi, ozet,
                egitimler, deneyimler, yetenekler, diller, fotoYolu);
    }

    public static void pdfOlustur(String adSoyad, String telefon, String eposta,
                                  String adres, DogumTarihi dogumTarihi, String ozet,
                                  List<Egitim> egitimler, List<IsDeneyimi> deneyimler,
                                  List<String> yetenekler, List<Dil> diller, String fotoYolu) {
        try {
            PDFOlusturucu pdf = new PDFOlusturucu();
            pdf.yeniSayfaBaslat();
            pdf.fotoEkle(fotoYolu);
            pdf.baslikEkle(adSoyad);
            pdf.iletisimBilgileriEkle(telefon, eposta, adres, dogumTarihi);
            pdf.ayiriciCizgiEkle();

            if (ozet != null && !ozet.isEmpty()) {
                pdf.bolumBaslikEkle("ÖZGEÇMİŞİ");
                pdf.paragrafEkle(ozet);
                pdf.bosluklEkle(15);
            }

            pdf.bolumBaslikEkle("EĞİTİM");
            for (Egitim egitim : egitimler) {
                pdf.egitimEkle(egitim);
            }
            pdf.bosluklEkle(10);

            pdf.bolumBaslikEkle("İŞ DENEYİMİ");
            for (IsDeneyimi deneyim : deneyimler) {
                pdf.deneyimEkle(deneyim);
            }
            pdf.bosluklEkle(10);

            pdf.bolumBaslikEkle("YETENEKLER");
            String yeteneklerStr = String.join(" - ", yetenekler);
            pdf.paragrafEkle(yeteneklerStr);
            pdf.bosluklEkle(15);

            pdf.bolumBaslikEkle("DİLLER");
            for (Dil dil : diller) {
                pdf.dilEkle(dil);
            }

            String fileName = "CV_" + adSoyad.replace(" ", "_") + ".pdf";
            pdf.kaydet(fileName);

        } catch (Exception e) {
            System.err.println("❌ PDF oluşturulurken hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}