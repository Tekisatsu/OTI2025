import java.util.Date;

public class Lasku {

    private int id;
    private int viitenumero;
    private Date erapaiva;
    private String maksaja;
    private String saaja;
    private String ytunnus;
    private double alvprosentti;
    private double maara;

    public Lasku(String id, String viitenumero, Date erapaiva, String maksaja, String saaja, String ytunnus, String alvprosentti, String maara) {
        this.id = Integer.parseInt(id);
        this.viitenumero = Integer.parseInt(viitenumero);
        this.erapaiva = erapaiva;
        this.maksaja = maksaja;
        this.saaja = saaja;
        this.ytunnus = ytunnus;
        this.alvprosentti = Double.parseDouble(alvprosentti);
        this.maara = Double.parseDouble(maara);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViitenumero() {
        return viitenumero;
    }

    public void setViitenumero(int viitenumero) {
        this.viitenumero = viitenumero;
    }

    public Date getErapaiva() {
        return erapaiva;
    }

    public void setErapaiva(Date erapaiva) {
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
    public String toString() {
        return "Lasku{" +
                "id=" + id +
                ", viitenumero='" + viitenumero +
                ", erapaiva=" + erapaiva + '\'' +
                ", maksaja='" + maksaja + '\'' +
                ", saaja=" + saaja + '\'' +
                ", ytunnus='" + ytunnus + '\'' +
                ", alvprosentti=" + alvprosentti +
                ", maara='" + maara +
                '}';
    }
}