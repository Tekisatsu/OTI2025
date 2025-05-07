import java.math.BigDecimal;
import java.time.LocalDate;

public class Raportti {

    private LocalDate alkuPvm;
    private LocalDate loppuPvm;
    private int varaustenMaara;
    private BigDecimal kokonaislaskutus;
    private String suosituinMokki;
    private int keskimKesto;
    private int asiakasmaara;

    public Raportti(LocalDate alkuPvm, LocalDate loppuPvm, int varaustenMaara,
                    BigDecimal kokonaislaskutus, String suosituinMokki,
                    int keskimKesto, int asiakasmaara) {
        this.alkuPvm = alkuPvm;
        this.loppuPvm = loppuPvm;
        this.varaustenMaara = varaustenMaara;
        this.kokonaislaskutus = kokonaislaskutus;
        this.suosituinMokki = suosituinMokki;
        this.keskimKesto = keskimKesto;
        this.asiakasmaara = asiakasmaara;
    }

    public LocalDate getAlkuPvm() {
        return alkuPvm;
    }

    public LocalDate getLoppuPvm() {
        return loppuPvm;
    }

    public int getVaraustenMaara() {
        return varaustenMaara;
    }

    public BigDecimal getKokonaislaskutus() {
        return kokonaislaskutus;
    }

    public String getSuosituinMokki() {
        return suosituinMokki;
    }

    public int getKeskimKesto() {
        return keskimKesto;
    }

    public int getAsiakasmaara() {
        return asiakasmaara;
    }

    public String toString() {
        return String.format(
                "Raportti ajalta %s - %s\n" +
                "Varausten määrä: %d\n" +
                "Kokonaislaskutus: %.2f€\n" +
                "Suosituin mökki: %s\n" +
                "Keskimääräinen varauksen kesto: %d päivää\n" +
                "Asiakasmäärä: %d\n",
                alkuPvm, loppuPvm, varaustenMaara, kokonaislaskutus,
                suosituinMokki, keskimKesto, asiakasmaara);
    }


}
