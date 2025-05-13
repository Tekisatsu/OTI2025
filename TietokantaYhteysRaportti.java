import java.sql.*;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * TietokantaYhteysRaportti-luokka sisältää kaikki metodit, joita tarvitaan käyttöliittymän ja
 * tietokannan yhdistämiseen raporttien osalta.
 */
public class TietokantaYhteysRaportti {

    private String url = "jdbc:mysql://localhost:3306/mokkikodit?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    /**
     * Luo yhteyden tietokantaa.
     * @return tietokantayhteys
     * @throws SQLException mikäli yhteyden luonti epäonnistuu
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    /**
     * Hakee varausten määrän tietokannasta halutulta päivämääräväliltä.
     * @param alku haluttu alkupäivämäärä
     * @param loppu haluttu päättymispäivämäärä
     * @return 0, mikäli virhe varausmäärän haussa
     */
    public int haeVaraustenMaara(LocalDate alku, LocalDate loppu) {
        String sql = "SELECT COUNT(*) FROM VARAUS WHERE Aloituspaivamaara >= ? AND Paattymispaivamaara <= ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(alku));
            stmt.setDate(2, Date.valueOf(loppu));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Virhe varausmäärän haussa: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Metodi varauksiin liitettyjen asiakkaiden hakuun halutulta päivämääräväliltä.
     * @param alku haluttu alkupäivämäärä
     * @param loppu haluttu päättymispäivämäärä
     * @return 0, mikäli virhe asiakasmäärän haussa
     */
    public int haeAsiakasmaara(LocalDate alku, LocalDate loppu) {
        String sql = """
                SELECT COUNT(DISTINCT Asiakas_ID)
                FROM VARAUS
                WHERE Aloituspaivamaara >= ? AND Paattymispaivamaara <= ?
                """;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(alku));
            stmt.setDate(2, Date.valueOf(loppu));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Virhe asiakasmäärän haussa: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Metodi kokonaislaskutuksen hakuun tietyltä aikaväliltä.
     * @param alku haluttu alkupäivämäärä
     * @param loppu haluttu päättymispäivämäärä
     * @return 0, mikäli virhe laskutuksen haussa
     */
    public BigDecimal haeKokonaislaskutus(LocalDate alku, LocalDate loppu) {
        String sql = """
                SELECT SUM(LASKU.Maara)
                FROM VARAUS
                JOIN LASKU ON VARAUS.Lasku_ID = LASKU.ID
                WHERE VARAUS.Aloituspaivamaara >= ? AND VARAUS.Paattymispaivamaara <= ?
                """;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(alku));
            stmt.setDate(2, Date.valueOf(loppu));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : BigDecimal.ZERO;
        } catch (SQLException e) {
            System.err.println("Virhe laskutuksen haussa: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    /**
     * Metodi hakee suosituimman mökin varauksien määrän perusteella tietyltä aikaväliltä.
     * @param alku tarkasteluvälin alkamispäivämäärä
     * @param loppu tarkasteluvälin päättymispäivämäärä
     * @return "Ei tietoa", mikäli virhe suosituimman mökin haussa
     */
    public String haeSuosituinMokki(LocalDate alku, LocalDate loppu) {
        String sql = """
                SELECT MOKKI.Nimi, COUNT(*) AS varauksia
                FROM VARAUS
                JOIN MOKKI ON VARAUS.Mokki_ID = MOKKI.ID
                WHERE VARAUS.Aloituspaivamaara >= ? AND VARAUS.Paattymispaivamaara <= ?
                GROUP BY MOKKI.ID
                ORDER BY varauksia DESC
                LIMIT 1
                """;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(alku));
            stmt.setDate(2, Date.valueOf(loppu));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("Nimi");
        } catch (SQLException e) {
            System.err.println("Virhe suosituimman mökin haussa: " + e.getMessage());
        }
        return "Ei tietoa";
    }

    /**
     * Metodi hakee varauksien keskimääräisen keston tietyltä aikaväliltä.
     * @param alku tarkasteluvälin alkamispäivämäärä
     * @param loppu tarkasteluvälin päättymispäivämäärä
     * @return 0, mikäli virhe keskimääräisen keston haussa
     */
    public int haeKeskimaarainenKesto(LocalDate alku, LocalDate loppu) {
        String sql = """
                SELECT AVG(DATEDIFF(Paattymispaivamaara, Aloituspaivamaara))
                FROM VARAUS
                WHERE Aloituspaivamaara >= ? AND Paattymispaivamaara <= ?
                """;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(alku));
            stmt.setDate(2, Date.valueOf(loppu));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Virhe keskimääräisen keston haussa: " + e.getMessage());
        }
        return 0;
    }
}
