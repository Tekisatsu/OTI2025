import java.time.LocalDate;

public class Varaus {
    int id;
    int asiakas_id;
    int mokki_id;
    int lasku_id;

    public Varaus(int id, int asiakas_id, int mokki_id, int lasku_id, LocalDate alkamispaivamaara, LocalDate paattumispaivamaara) {
        this.id = id;
        this.asiakas_id = asiakas_id;
        this.mokki_id = mokki_id;
        this.lasku_id = lasku_id;
        this.alkamispaivamaara = alkamispaivamaara;
        this.paattumispaivamaara = paattumispaivamaara;
    }

    LocalDate alkamispaivamaara;
    LocalDate paattumispaivamaara;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAsiakas_id() {
        return asiakas_id;
    }

    public void setAsiakas_id(int asiakas_id) {
        this.asiakas_id = asiakas_id;
    }

    public int getMokki_id() {
        return mokki_id;
    }

    public void setMokki_id(int mokki_id) {
        this.mokki_id = mokki_id;
    }

    public int getLasku_id() {
        return lasku_id;
    }

    public void setLasku_id(int lasku_id) {
        this.lasku_id = lasku_id;
    }

    public LocalDate getAlkamispaivamaara() {
        return alkamispaivamaara;
    }

    public void setAlkamispaivamaara(LocalDate alkamispaivamaara) {
        this.alkamispaivamaara = alkamispaivamaara;
    }

    public LocalDate getPaattumispaivamaara() {
        return paattumispaivamaara;
    }

    public void setPaattumispaivamaara(LocalDate paattumispaivamaara) {
        this.paattumispaivamaara = paattumispaivamaara;
    }

    @Override
    public String toString() {
        return "Varaus{" +
                "id=" + id +
                ", asiakas_id=" + asiakas_id +
                ", mokki_id=" + mokki_id +
                ", lasku_id=" + lasku_id +
                ", alkamispaivamaara=" + alkamispaivamaara +
                ", paattumispaivamaara=" + paattumispaivamaara +
                '}';
    }
}