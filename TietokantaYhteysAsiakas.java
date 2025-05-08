import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TietokantaYhteysAsiakas {
    private String url = "jdbc:mysql://localhost:3306/mokkikodit?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    private Osoite getOsoiteTK(int osoiteId) {
        String sql = "SELECT * FROM OSOITE WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, osoiteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Osoite osoite = new Osoite();
                osoite.setId(rs.getInt("ID"));
                osoite.setOsoite(rs.getString("Katuosoite"));
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

    // luo asiakastiedot
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

    // lue asiakastiedot
    public Asiakas getAsiakastiedot(int id) {
        String sql = "SELECT * FROM ASIAKAS WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Osoite osoite = getOsoiteTK(rs.getInt("Osoite_id"));
                return new Asiakas(
                        String.valueOf(rs.getInt("ID")),
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

    // lue kaikki asiakkaat
    public List<Asiakas> getAllAsiakkaat() {
        List<Asiakas> asiakkaat = new ArrayList<>();
        String sql = "SELECT * FROM ASIAKAS";
        try (Connection yhteys = getConnection(); Statement stmt = yhteys.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Osoite osoite = getOsoiteTK(rs.getInt("Osoite_id"));
                Asiakas asiakas = new Asiakas(
                        String.valueOf(rs.getInt("ID")),
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

    // päivitä asiakastiedot
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

    // poista asiakastiedot
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
