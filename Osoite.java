public class Osoite {
    int id;
    String katuosoite;
    String kaupunki;
    String maa;
    int zip;

    public Osoite() {
        katuosoite = "";
        kaupunki = "";
        maa = "";
        zip = 0;
    }

    public Osoite(String id, String osoite, String kaupunki, String maa, String zip) {
        this.id = Integer.parseInt(id);
        this.katuosoite = osoite;
        this.kaupunki = kaupunki;
        this.maa = maa;
        this.zip = Integer.parseInt(zip);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public String getKaupunki() {
        return kaupunki;
    }

    public void setKaupunki(String kaupunki) {
        this.kaupunki = kaupunki;
    }

    public String getMaa() {
        return maa;
    }

    public void setMaa(String maa) {
        this.maa = maa;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return ""+ katuosoite +", "+kaupunki+", "+zip+", "+maa;
    }
}
