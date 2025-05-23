import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka, joka hallinnoi asiakastietoja tietokannassa.
 */
public class TietokantaYhteysAsiakas {
    private String url = "jdbc:mysql://localhost:3306/mokkikodit?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    /**
     * Luo yhteyden tietokantaan.
     * @return tietokantayhteys
     * @throws SQLException mikäli yhteyden luonti epäonnistuu
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    /**
     * Lukee osoitetiedot tietokannasta osoite-ID:n perusteella.
     * @param osoiteId osoitteen ID
     * @return osoite-olio, mikäli löytyy, muutoin null
     */
    private Osoite getOsoiteTK(int osoiteId) {
        String sql = "SELECT * FROM OSOITE WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, osoiteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Osoite osoite = new Osoite();
                osoite.setId(rs.getInt("ID"));
                osoite.setKatuosoite(rs.getString("Katuosoite"));
                osoite.setKaupunki(rs.getString("Kaupunki"));
                osoite.setMaa(rs.getString("Maa"));
                osoite.setZip(rs.getInt("Postinumero"));
                return osoite;
            }
        } catch (SQLException e) {
            System.err.println("Virhe osoitteen lukemisessa: " + e.getMessage());
        }
        return null;
    }

    /**
     * Luo uuden asiakastiedon tietokantaan.
     * @param asiakas asiakastiedot
     */
    public void createAsiakas(Asiakas asiakas) {
        String sql = "INSERT INTO ASIAKAS (Nimi, Sahkoposti, Puhelinnumero, Osoite_id) VALUES (?, ?, ?, ?)";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, asiakas.getNimi());
            stmt.setString(2, asiakas.getSahkoposti());
            stmt.setString(3, asiakas.getPuhelinnumero());
            stmt.setInt(4, asiakas.getOsoite().getId());
            stmt.executeUpdate();
            System.out.println("Asiakastiedot luotu nimelle " + asiakas.getNimi());
        } catch (SQLException e) {
            System.err.println("Virhe asiakastietojen luonnissa: " + e.getMessage());
        }
    }

    /**
     * Hakee asiakastiedot ID:n perusteella.
     * @param id asiakkaan ID
     * @return asiakas-olio, mikäli löytyy, muuten null
     */
    public Asiakas getAsiakastiedot(int id) {
        String sql = "SELECT * FROM ASIAKAS WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Osoite osoite = getOsoiteTK(rs.getInt("Osoite_id"));
                return new Asiakas(
                        rs.getInt("ID"),
                        rs.getString("Nimi"),
                        rs.getString("Sahkoposti"),
                        rs.getString("Puhelinnumero"),
                        osoite
                );
            }
        } catch (SQLException e) {
            System.err.println("Virhe asiakastietojen lukemisessa: " + e.getMessage());
        }
        return null;
    }

    /**
     * Hakee kaikkien asiakkaiden tiedot tietokannasta.
     * @return lista asiakas-olioista
     */
    public List<Asiakas> getAllAsiakkaat() {
        List<Asiakas> asiakkaat = new ArrayList<>();
        String sql = "SELECT * FROM ASIAKAS";
        try (Connection yhteys = getConnection(); Statement stmt = yhteys.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Osoite osoite = getOsoiteTK(rs.getInt("Osoite_id"));
                Asiakas asiakas = new Asiakas(
                        rs.getInt("ID"),
                        rs.getString("Nimi"),
                        rs.getString("Sahkoposti"),
                        rs.getString("Puhelinnumero"),
                        osoite
                );
                asiakkaat.add(asiakas);
            }
        } catch (SQLException e) {
            System.err.println("Virhe asiakastietojen lukemisessa: " + e.getMessage());
        }
        return asiakkaat;
    }

    /**
     * Päivittää asiakastiedot tietokannassa.
     * @param asiakas päivitettävät asiakastiedot
     */
    public void updateAsiakas(Asiakas asiakas) {
        String sql = "UPDATE ASIAKAS SET Nimi = ?, Sahkoposti = ?, Puhelinnumero = ?, Osoite_id = ? WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, asiakas.getNimi());
            stmt.setString(2, asiakas.getSahkoposti());
            stmt.setString(3, asiakas.getPuhelinnumero());
            stmt.setInt(4, asiakas.getOsoite().getId());
            stmt.setInt(5, asiakas.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Asiakastiedot päivitetty nimelle " + asiakas.getNimi());
            } else {
                System.out.println("Ei asiakastietoja ID:llä: " + asiakas.getId());
            }
        } catch (SQLException e) {
            System.err.println("Virhe asiakastietojen päivityksessä: " + e.getMessage());
        }
    }

    /**
     * Poistaa asiakastiedot tietokannasta.
     * @param id poistettavan asiakkaan ID
     */
    public void deleteAsiakas(int id) {
        String sql = "DELETE FROM ASIAKAS WHERE ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Asiakas poistettu ID:llä " + id);
            } else {
                System.out.println("Ei asiakasta ID:llä " + id);
            }
        } catch (SQLException e) {
            System.err.println("Virhe asiakastietojen poistamisessa: " + e.getMessage());
        }
    }
}
