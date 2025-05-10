import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TietokantaYhteysMokki {
    private String url = "jdbc:mysql://localhost:3306/mokkikodit?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    // osoite haku taulusta
    public Osoite getOsoite(int osoiteId) {
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

    // luo mökki tietokantaan
    public void createMokki(Mokki mokki) {
        String sql = "INSERT INTO MOKKI (Osoite_id, Vuokrahinta, Nimi, Tila) VALUES (?, ?, ?, ?)";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, mokki.getOsoite().getId());
            stmt.setDouble(2, mokki.getVuokrahinta());
            stmt.setString(3, mokki.getNimi());
            stmt.setString(4, mokki.getTila());
            stmt.executeUpdate();
            System.out.println("Mokki luotu: " + mokki.getNimi());
        } catch (SQLException e) {
            System.err.println("Virhe mökin luonnissa: " + e.getMessage());
        }
    }

    // lue mökki ID:n perusteella
    public Mokki readMokki(int id) {
        String sql = "SELECT * FROM MOKKI WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Mokki mokki = new Mokki();
                mokki.setId(rs.getInt("ID"));
                mokki.setOsoite(getOsoite(rs.getInt("Osoite_id")));
                mokki.setVuokrahinta(rs.getDouble("Vuokrahinta"));
                mokki.setNimi(rs.getString("Nimi"));
                mokki.setTila(rs.getString("Tila"));
                return mokki;
            }
        } catch (SQLException e) {
            System.err.println("Virhe mökin lukemissa: " + e.getMessage());
        }
        return null;
    }

    // lue kaikki mökit
    public List<Mokki> readAllMokit() {
        List<Mokki> mokit = new ArrayList<>();
        String sql = "SELECT * FROM MOKKI";
        try (Connection yhteys = getConnection(); Statement stmt = yhteys.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Mokki mokki = new Mokki();
                mokki.setId(rs.getInt("ID"));
                mokki.setOsoite(getOsoite(rs.getInt("Osoite_id")));
                mokki.setVuokrahinta(rs.getDouble("Vuokrahinta"));
                mokki.setNimi(rs.getString("Nimi"));
                mokki.setTila(rs.getString("Tila"));
                mokit.add(mokki);
            }
        } catch (SQLException e) {
            System.err.println("Virhe mökkien lukemisessa: " + e.getMessage());
        }
        return mokit;
    }

    // päivitä mökki
    public void updateMokki(Mokki mokki) {
        String sql = "UPDATE MOKKI SET Osoite_id = ?, Vuokrahinta = ?, Nimi = ?, Tila = ? WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, mokki.getOsoite().getId());
            stmt.setDouble(2, mokki.getVuokrahinta());
            stmt.setString(3, mokki.getNimi());
            stmt.setString(4, mokki.getTila());
            stmt.setInt(5, mokki.getId());
            int kohdeRivit = stmt.executeUpdate();
            if (kohdeRivit > 0) {
                System.out.println("Mökki päivitetty: " + mokki.getNimi());
            } else {
                System.out.println("Ei mökkiä ID:llä " + mokki.getId());
            }
        } catch (SQLException e) {
            System.err.println("Virhe mökin päivyksessä: " + e.getMessage());
        }
    }

    // poista mökki
    public void deleteMokki(int id) {
        String sql = "DELETE FROM MOKKI WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int kohdeRivit = stmt.executeUpdate();
            if (kohdeRivit > 0) {
                System.out.println("Mökki poistettu ID:llä " + id);
            } else {
                System.out.println("Ei mökkiä ID:llä " + id);
            }
        } catch (SQLException e) {
            System.err.println("Virhe mökin poistamisessa: " + e.getMessage());
        }
    }
}
