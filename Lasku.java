import java.time.LocalDate;
import java.util.Objects;

public class Lasku {

    private int id;
    private String viitenumero;
    private LocalDate erapaiva;
    private String maksaja;
    private String saaja;
    private String ytunnus;
    private double alvprosentti;
    private double maara;

    public Lasku(int id, String viitenumero, LocalDate erapaiva, String maksaja, String saaja, String ytunnus, double alvprosentti, double maara) {
        this.id = id;
        this.viitenumero = viitenumero;
        this.erapaiva = erapaiva;
        this.maksaja = maksaja;
        this.saaja = saaja;
        this.ytunnus = ytunnus;
        this.alvprosentti = alvprosentti;
        this.maara = maara;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getViitenumero() {
        return viitenumero;
    }

    public void setViitenumero(String viitenumero) {
        this.viitenumero = viitenumero;
    }

    public LocalDate getErapaiva() {
        return erapaiva;
    }

    public void setErapaiva(LocalDate erapaiva) {
        this.erapaiva = erapaiva;
    }

    public String getMaksaja() {
        return maksaja;
    }

    public void setMaksaja(String maksaja) {
        this.maksaja = maksaja;
    }

    public String getSaaja() {
        return saaja;
    }

    public void setSaaja(String saaja) {
        this.saaja = saaja;
    }

    public String getYtunnus() {
        return ytunnus;
    }

    public void setYtunnus(String ytunnus) {
        this.ytunnus = ytunnus;
    }

    public double getAlvprosentti() {
        return alvprosentti;
    }

    public void setAlvprosentti(double alvprosentti) {
        this.alvprosentti = alvprosentti;
    }

    public double getMaara() {
        return maara;
    }

    public void setMaara(double maara) {
        this.maara = maara;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Lasku lasku = (Lasku) o;
        return Double.compare(alvprosentti, lasku.alvprosentti) == 0 && Double.compare(maara, lasku.maara) == 0 && Objects.equals(viitenumero, lasku.viitenumero) && Objects.equals(erapaiva, lasku.erapaiva) && Objects.equals(maksaja, lasku.maksaja) && Objects.equals(saaja, lasku.saaja) && Objects.equals(ytunnus, lasku.ytunnus);
    }

    @Override
    public String toString() {
        return viitenumero+", "+erapaiva+", "+maksaja+", "+maara;
    }
}
