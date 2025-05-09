import java.util.Objects;

public class Mokki {
    int id;
    String name;
    Osoite osoite;
    String tila;
    double vuokrahinta;

    public Mokki() {
        this.id = 0;
        this.name = "";
        this.osoite = new Osoite();
        this.tila = "";
        this.vuokrahinta = 0;
    }
    public Mokki(int id, String name, Osoite osoite, String tila, double vuokrahinta) {
        this.id = id;
        this.name = name;
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Mokki mokki = (Mokki) o;
        return Double.compare(vuokrahinta, mokki.vuokrahinta) == 0 && Objects.equals(name, mokki.name) && Objects.equals(osoite, mokki.osoite) && Objects.equals(tila, mokki.tila);
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
