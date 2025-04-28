public class Mokki {
    int id;
    String name;
    Osoite osoite;
    String tila;

    public Mokki(int id, String name, Osoite osoite, String tila, double vuokrahinta) {
        this.id = id;
        this.name = name;
        this.osoite = osoite;
        this.tila = tila;
        this.vuokrahinta = vuokrahinta;
    }

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

    @Override
    public String toString() {
        return "Mokki{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", osoite=" + osoite +
                ", tila='" + tila + '\'' +
                ", vuokrahinta=" + vuokrahinta +
                '}';
    }
}
