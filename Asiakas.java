import java.util.Objects;

public class Asiakas {

    private int id;
    private String nimi;
    private String sahkoposti;
    private String puhelinnumero;
    private Osoite osoite;

    public Asiakas() {
        this.id = 0;
        this.nimi = "";
        this.sahkoposti = "";
        this.puhelinnumero = "";
        this.osoite = new Osoite();
    }

    public Asiakas(String id, String nimi, String sahkoposti, String puhelinnumero, Osoite osoite) {
        this.id = Integer.parseInt(id);
        this.nimi = nimi;
        this.sahkoposti = sahkoposti;
        this.puhelinnumero = puhelinnumero;
        this.osoite = osoite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    public String getPuhelinnumero() {
        return puhelinnumero;
    }

    public void setPuhelinnumero(String puhelinnumero) {
        this.puhelinnumero = puhelinnumero;
    }

    public Osoite getOsoite() {
        return osoite;
    }

    public void setOsoite(Osoite osoite) {
        this.osoite = osoite;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Asiakas asiakas = (Asiakas) o;
        return Objects.equals(nimi, asiakas.nimi) && Objects.equals(sahkoposti, asiakas.sahkoposti) && Objects.equals(puhelinnumero, asiakas.puhelinnumero) && Objects.equals(osoite, asiakas.osoite);
    }

    @Override
    public String toString() {
        return "Asiakas{" +
                "id=" + id +
                ", nimi='" + nimi + '\'' +
                ", sahkoposti=" + sahkoposti + '\'' +
                ", puhelinnumero='" + puhelinnumero + '\'' +
                ", osoite=" + osoite +
                '}';
    }
}