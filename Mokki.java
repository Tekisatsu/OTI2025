public class Mokki {
    int id;
    String name;
    Osoite osoite;
    String tila;
    double vuokrahinta;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
