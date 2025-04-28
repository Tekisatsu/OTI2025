public class Osoite {
    String osoite;
    String kaupunki;

    public Osoite(String osoite, String kaupunki, String maa, int zip) {
        this.osoite = osoite;
        this.kaupunki = kaupunki;
        this.maa = maa;
        this.zip = zip;
    }

    String maa;
    int zip;

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
        return "Osoite{" +
                "osoite='" + osoite + '\'' +
                ", kaupunki='" + kaupunki + '\'' +
                ", maa='" + maa + '\'' +
                ", zip=" + zip +
                '}';
    }
}
