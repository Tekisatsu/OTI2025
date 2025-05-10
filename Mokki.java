import java.util.Objects;

public class Mokki {
    int id;
    String nimi;
    Osoite osoite;
    String tila;
    double vuokrahinta;

    public Mokki() {
        this.id = 0;
        this.nimi = "";
        this.osoite = new Osoite();
        this.tila = "";
        this.vuokrahinta = 0;
    }
    public Mokki(int id, String nimi, Osoite osoite, String tila, double vuokrahinta) {
        this.id = id;
        this.nimi = nimi;
        this.osoite = osoite;
        this.tila = tila;
        this.vuokrahinta = vuokrahinta;
    }

    public double getVuokrahinta() {
        return vuokrahinta;
    }

    public void setVuokrahinta(double vuokrahinta) {
        this.vuokrahinta = vuokrahinta;
    }

    public String getTila() {
        return tila;
    }

    public void setTila(String tila) {
        this.tila = tila;
    }

    public Osoite getOsoite() {
        return osoite;
    }

    public void setOsoite(Osoite osoite) {
        this.osoite = osoite;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Mokki mokki = (Mokki) o;
        return Double.compare(vuokrahinta, mokki.vuokrahinta) == 0 && Objects.equals(nimi, mokki.nimi) && Objects.equals(osoite, mokki.osoite) && Objects.equals(tila, mokki.tila);
    }

    @Override
    public String toString() {
        return nimi+", "+osoite;
    }
}
