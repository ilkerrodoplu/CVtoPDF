package com.klu;

import java.util.HashMap;
import java.util.Map;

public class DogumTarihi {
    private String gun;
    private String ay;
    private String yil;

    private static final Map<String, String> AY_HARITALARI = new HashMap<>();

    static {
        // Sayı -> Ay adı
        AY_HARITALARI.put("01", "OCAK");
        AY_HARITALARI.put("02", "SUBAT");
        AY_HARITALARI.put("03", "MART");
        AY_HARITALARI.put("04", "NISAN");
        AY_HARITALARI.put("05", "MAYIS");
        AY_HARITALARI.put("06", "HAZIRAN");
        AY_HARITALARI.put("07", "TEMMUZ");
        AY_HARITALARI.put("08", "AGUSTOS");
        AY_HARITALARI.put("09", "EYLUL");
        AY_HARITALARI.put("10", "EKIM");
        AY_HARITALARI.put("11", "KASIM");
        AY_HARITALARI.put("12", "ARALIK");

        // Rakam -> Ay adı (başında 0 olmadan)
        AY_HARITALARI.put("1", "OCAK");
        AY_HARITALARI.put("2", "SUBAT");
        AY_HARITALARI.put("3", "MART");
        AY_HARITALARI.put("4", "NISAN");
        AY_HARITALARI.put("5", "MAYIS");
        AY_HARITALARI.put("6", "HAZIRAN");
        AY_HARITALARI.put("7", "TEMMUZ");
        AY_HARITALARI.put("8", "AGUSTOS");
        AY_HARITALARI.put("9", "EYLUL");
    }

    public DogumTarihi(String tarih) {
        tarihiAyir(tarih);
    }

    private void tarihiAyir(String tarih) {
        // Boşluk, nokta, tire, slash ile ayrılmış tarihleri ayır
        String[] parcalar = tarih.split("[ ./\\-]+");

        if (parcalar.length == 3) {
            this.gun = parcalar[0].trim();
            String ayGirdi = parcalar[1].trim();
            this.yil = parcalar[2].trim();

            // Ay işleme
            this.ay = ayIsle(ayGirdi);

            // Debug için
            System.out.println("Girilen ay: '" + ayGirdi + "' -> Dönüştürülen: '" + this.ay + "'");
        } else {
            // Geçersiz format, olduğu gibi al
            this.gun = "";
            this.ay = "";
            this.yil = tarih;
        }
    }

    private String ayIsle(String ayGirdi) {
        // Eğer sayı ise
        if (ayGirdi.matches("\\d+")) {
            // Ay haritasında varsa döndür
            if (AY_HARITALARI.containsKey(ayGirdi)) {
                return AY_HARITALARI.get(ayGirdi);
            }
            return ayGirdi; // Bulunamazsa olduğu gibi döndür
        } else {
            // Metin ise, tüm harfleri büyüt
            return ayGirdi.toUpperCase();
        }
    }

    public String getGun() {
        return gun;
    }

    public String getAy() {
        return ay;
    }

    public String getYil() {
        return yil;
    }

    public String getFormatliFull() {
        return gun + " " + ay + " " + yil;
    }

    public String getFormatliKisa() {
        return gun + "." + ay + "." + yil;
    }

    @Override
    public String toString() {
        return getFormatliFull();
    }
}