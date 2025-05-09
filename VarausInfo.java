import java.time.LocalDate;

public class VarausInfo {
    int id;
    Asiakas asiakas;
    Mokki mokki;
    Lasku lasku;
    int henkilomaara;
    LocalDate alkamispaivamaara;
    LocalDate paattumispaivamaara;

    public VarausInfo(int id, Asiakas asiakas, Mokki mokki, Lasku lasku, int henkilomaara, LocalDate alkamispaivamaara, LocalDate paattumispaivamaara) {
        this.id = id;
        this.asiakas = asiakas;
        this.mokki = mokki;
        this.lasku = lasku;
        this.henkilomaara = henkilomaara;
        this.alkamispaivamaara = alkamispaivamaara;
        this.paattumispaivamaara = paattumispaivamaara;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Asiakas getAsiakas() {
        return asiakas;
    }

    public void setAsiakas(Asiakas asiakas) {
        this.asiakas = asiakas;
    }

    public Mokki getMokki() {
        return mokki;
    }

    public void setMokki(Mokki mokki) {
        this.mokki = mokki;
    }

    public Lasku getLasku() {
        return lasku;
    }

    public void setLasku(Lasku lasku) {
        this.lasku = lasku;
    }

    public int getHenkilomaara() {
        return henkilomaara;
    }

    public void setHenkilomaara(int henkilomaara) {
        this.henkilomaara = henkilomaara;
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
        return "VarausInfo{" +
                "id=" + id +
                ", asiakas=" + asiakas +
                ", mokki=" + mokki +
                ", lasku=" + lasku +
                ", henkilomaara=" + henkilomaara +
                ", alkamispaivamaara=" + alkamispaivamaara +
                ", paattumispaivamaara=" + paattumispaivamaara +
                '}';
    }
}