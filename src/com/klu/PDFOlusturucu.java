package com.klu;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.*;
import java.util.*;

public class PDFOlusturucu {
    private PDFont turkceFont;
    private PDDocument document;
    private PDPage currentPage;
    private PDPageContentStream contentStream;
    private float yPosition;
    private float margin = 50;
    private float pageWidth;

    public PDFOlusturucu() throws IOException {
        document = new PDDocument();
        fontYukle();
    }

    private void fontYukle() throws IOException {
        try {
            turkceFont = PDType0Font.load(document, new File("arial.ttf"));
            System.out.println("✓ Arial font yüklendi");
        } catch (Exception e) {
            turkceFont = PDType0Font.load(document,
                    PDFOlusturucu.class.getResourceAsStream("/org/apache/pdfbox/resources/ttf/LiberationSans-Regular.ttf"));
            System.out.println("✓ LiberationSans font yüklendi");
        }
    }

    public void yeniSayfaBaslat() throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
        currentPage = new PDPage(PDRectangle.A4);
        document.addPage(currentPage);
        contentStream = new PDPageContentStream(document, currentPage);
        yPosition = currentPage.getMediaBox().getHeight() - margin;
        pageWidth = currentPage.getMediaBox().getWidth();
    }

    public void fotoEkle(String fotoYolu) throws IOException {
        if (fotoYolu == null || fotoYolu.isEmpty()) {
            return;
        }

        try {
            File imgFile = new File(fotoYolu);
            if (!imgFile.exists()) {
                System.out.println("✗ Dosya bulunamadı: " + imgFile.getAbsolutePath());
            } else {
                PDImageXObject image = PDImageXObject.createFromFileByContent(imgFile, document);
                float imgWidth = 60;
                float imgHeight = 60;
                contentStream.drawImage(image, pageWidth - margin - imgWidth,
                        yPosition - imgHeight, imgWidth, imgHeight);
                System.out.println("✓ Fotoğraf eklendi!");
            }
        } catch (Exception e) {
            System.out.println("✗ Fotoğraf yüklenemedi: " + e.getMessage());
        }
    }

    public void baslikEkle(String baslik) throws IOException {
        contentStream.beginText();
        contentStream.setFont(turkceFont, 24);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(baslik.toUpperCase());
        contentStream.endText();
        yPosition -= 40;
    }

    public void iletisimBilgileriEkle(String telefon, String eposta, String adres, DogumTarihi dogumTarihi) throws IOException {
        contentStream.beginText();
        contentStream.setFont(turkceFont, 10);
        contentStream.newLineAtOffset(margin, yPosition);
        String iletisim = telefon + " | " + eposta;
        contentStream.showText(iletisim);
        contentStream.endText();
        yPosition -= 15;

        contentStream.beginText();
        contentStream.setFont(turkceFont, 10);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(adres + " | " + dogumTarihi.getFormatliFull());
        contentStream.endText();
        yPosition -= 25;
    }

    public void ayiriciCizgiEkle() throws IOException {
        contentStream.setLineWidth(1f);
        contentStream.moveTo(margin, yPosition);
        contentStream.lineTo(pageWidth - margin, yPosition);
        contentStream.stroke();
        yPosition -= 20;
    }

    public void bolumBaslikEkle(String baslik) throws IOException {
        sayfaKontrol(50);
        contentStream.beginText();
        contentStream.setFont(turkceFont, 14);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(baslik);
        contentStream.endText();
        yPosition -= 20;
    }

    public void paragrafEkle(String text) throws IOException {
        if (text == null || text.isEmpty()) {
            return;
        }

        contentStream.setFont(turkceFont, 10);
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            float textWidth = turkceFont.getStringWidth(testLine) / 1000 * 10;

            if (textWidth > (pageWidth - 2 * margin)) {
                sayfaKontrol(30);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(line.toString());
                contentStream.endText();
                yPosition -= 15;
                line = new StringBuilder(word);
            } else {
                line = new StringBuilder(testLine);
            }
        }

        if (line.length() > 0) {
            sayfaKontrol(30);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText(line.toString());
            contentStream.endText();
            yPosition -= 15;
        }
    }

    public void egitimEkle(Egitim egitim) throws IOException {
        sayfaKontrol(100);

        contentStream.beginText();
        contentStream.setFont(turkceFont, 11);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(egitim.okul);
        contentStream.endText();
        yPosition -= 15;

        contentStream.beginText();
        contentStream.setFont(turkceFont, 10);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(egitim.bolum + " | " + egitim.baslangic + " - " + egitim.bitis);
        contentStream.endText();
        yPosition -= 20;
    }

    public void deneyimEkle(IsDeneyimi deneyim) throws IOException {
        sayfaKontrol(150);

        contentStream.beginText();
        contentStream.setFont(turkceFont, 11);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(deneyim.pozisyon + " - " + deneyim.sirket);
        contentStream.endText();
        yPosition -= 15;

        contentStream.beginText();
        contentStream.setFont(turkceFont, 10);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(deneyim.baslangic + " - " + deneyim.bitis);
        contentStream.endText();
        yPosition -= 15;

        paragrafEkle(deneyim.gorev);
        yPosition -= 15;
    }

    public void dilEkle(Dil dil) throws IOException {
        sayfaKontrol(30);
        contentStream.beginText();
        contentStream.setFont(turkceFont, 10);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(dil.dil + " - " + dil.seviye);
        contentStream.endText();
        yPosition -= 15;
    }

    public void bosluklEkle(int miktar) {
        yPosition -= miktar;
    }

    private void sayfaKontrol(int gerekliBosluk) throws IOException {
        if (yPosition < gerekliBosluk) {
            yeniSayfaBaslat();
        }
    }

    public void kaydet(String dosyaAdi) throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
        document.save(dosyaAdi);
        document.close();
        System.out.println("\n✓ CV başarıyla oluşturuldu: " + dosyaAdi);
    }

    public void kapat() throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
        if (document != null) {
            document.close();
        }
    }
}