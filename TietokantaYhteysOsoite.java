import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TietokantaYhteysOsoite {
    private String url = "jdbc:mysql://localhost:3306/mokkikodit?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    // luo osoitetiedot
    public void createOsoite(Osoite osoite) {
        String sql = "INSERT INTO OSOITE (Katuosoite, Kaupunki, Maa, Postinumero) VALUES (?, ?, ?, ?)";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, osoite.getKatuosoite());
            stmt.setString(2, osoite.getKaupunki());
            stmt.setString(3, osoite.getMaa());
            stmt.setInt(4, osoite.getZip());
            stmt.executeUpdate();
            System.out.println("Osoitetiedot luotu katuosoitteelle " + osoite.getKatuosoite());
        } catch (SQLException e) {
            System.err.println("Virhe osoitetietojen luonnissa: " + e.getMessage());
        }
    }

    // lue kaikki osoitteet
    public List<Osoite> getAllOsoitteet() {
        List<Osoite> osoitteet = new ArrayList<>();
        String sql = "SELECT * FROM OSOITE";
        try (Connection yhteys = getConnection(); Statement stmt = yhteys.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Osoite osoite = new Osoite(
                        String.valueOf(rs.getInt("ID")),
                        rs.getString("Katuosoite"),
                        rs.getString("Kaupunki"),
                        rs.getString("Maa"),
                        rs.getString("Postinumero")
                );
                osoitteet.add(osoite);
            }
        } catch (SQLException e) {
            System.err.println("Virhe osoitteiden luennassa: " + e.getMessage());
        }
        return osoitteet;
    }

    // päivitä osoite
    public void updateOsoite(Osoite osoite) {
        String sql = "UPDATE OSOITE SET Katuosoite = ?, Kaupunki = ?, Maa = ?, Postinumero = ? WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, osoite.getKatuosoite());
            stmt.setString(2, osoite.getKaupunki());
            stmt.setString(3, osoite.getMaa());
            stmt.setString(4, String.valueOf(osoite.getZip()));
            stmt.setInt(5, osoite.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Osoite päivitetty ID:llä " + osoite.getId());
            } else {
                System.out.println("Ei osoitetta ID:llä " + osoite.getId());
            }
        } catch (SQLException e) {
            System.err.println("Virhe osoitteen päivityksessä: " + e.getMessage());
        }
    }

    // poista osoite
    public void deleteOsoite(int id) {
        String sql = "DELETE FROM OSOITE WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Osoite poistettu ID:llä " + id);
            } else {
                System.out.println("Ei osoitetta ID:llä " + id);
            }
        } catch (SQLException e) {
            System.err.println("Virhe osoitteen poistamisessa: " + e.getMessage());
        }
    }
}


