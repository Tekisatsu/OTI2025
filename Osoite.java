public class Osoite {
    int id;
    String osoite;
    String kaupunki;
    String maa;
    int zip;

    public Osoite() {
        osoite = "";
        kaupunki = "";
        maa = "";
        zip = 0;
    }

    public Osoite(String osoite, String kaupunki, String maa, int zip) {
        this.osoite = osoite;
        this.kaupunki = kaupunki;
        this.maa = maa;
        this.zip = zip;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getOsoite() {
        return osoite;
    }

    public void setOsoite(String osoite) {
        this.osoite = osoite;
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
        return ""+osoite+", "+kaupunki+", "+zip+", "+maa;
    }
}
