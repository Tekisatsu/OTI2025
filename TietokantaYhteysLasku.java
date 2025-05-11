import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka, joka hallinnoi laskujen tietoja tietokannassa.
 */
public class TietokantaYhteysLasku {
    private String url = "jdbc:mysql://localhost:3306/mokkikodit?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    /**
     * Luo yhteyden tietokantaan.
     * @return tietokantayhteys
     * @throws SQLException jos yhteyden luominen epäonnistuu
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    /**
     * Luo uuden laskun tietokantaan.
     * @param lasku laskun tiedot
     */
    public void createLasku(Lasku lasku) {
        String sql = "INSERT INTO LASKU (Viitenumero, Erapaiva, Maksaja, Saaja, Y_tunnus, ALV_prosentti, Maara) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, lasku.getViitenumero());
            stmt.setDate(2, java.sql.Date.valueOf(lasku.getErapaiva()));
            stmt.setString(3, lasku.getMaksaja());
            stmt.setString(4, lasku.getSaaja());
            stmt.setString(5, lasku.getYtunnus());
            stmt.setDouble(6, lasku.getAlvprosentti());
            stmt.setDouble(7, lasku.getMaara());
            stmt.executeUpdate();
            System.out.println("Lasku luotu viitenumerolla " + lasku.getViitenumero());
        } catch (SQLException e) {
            System.err.println("Virhe laskun luonnissa: " + e.getMessage());
        }
    }

    /**
     * Lukee laskun tiedot ID:n perusteella.
     * @param id laskun ID
     * @return lasku-olio, mikäli löytyy, muutoin null
     */
    public Lasku getLaskuID(int id) {
        String sql = "SELECT * FROM LASKU WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Lasku(
                        rs.getInt("ID"),
                        rs.getString("Viitenumero"),
                        rs.getDate("Erapaiva").toLocalDate(),
                        rs.getString("Maksaja"),
                        rs.getString("Saaja"),
                        rs.getString("Y_tunnus"),
                        rs.getDouble("ALV_prosentti"),
                        rs.getDouble("Maara")
                );
            }
        } catch (SQLException e) {
            System.err.println("Virhe laskun luennassa: " + e.getMessage());
        }
        return null;
    }

    /**
     * Hakee kaikkien laskujen tiedot tietokannasta.
     * @return lista lasku-olioista
     */
    public List<Lasku> getAllLaskut() {
        List<Lasku> laskut = new ArrayList<>();
        String sql = "SELECT * FROM LASKU";
        try (Connection yhteys = getConnection(); Statement stmt = yhteys.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Lasku lasku = new Lasku(
                        rs.getInt("ID"),
                        rs.getString("Viitenumero"),
                        rs.getDate("Erapaiva").toLocalDate(),
                        rs.getString("Maksaja"),
                        rs.getString("Saaja"),
                        rs.getString("Y_tunnus"),
                        rs.getDouble("ALV_prosentti"),
                        rs.getDouble("Maara")
                );
                laskut.add(lasku);
            }
        } catch (SQLException e) {
            System.err.println("Virhe laskujen luennassa: " + e.getMessage());
        }
        return laskut;
    }

    /**
     * Päivittää laskun tiedot tietokannassa.
     * @param lasku päivitettävät laskun tiedot
     */
    public void updateLasku(Lasku lasku) {
        String sql = "UPDATE LASKU SET Viitenumero = ?, Erapaiva = ?, Maksaja = ?, Saaja = ?, Y_tunnus = ?, " +
                "ALV_prosentti = ?, Maara = ? WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, lasku.getViitenumero());
            stmt.setDate(2, java.sql.Date.valueOf(lasku.getErapaiva()));
            stmt.setString(3, lasku.getMaksaja());
            stmt.setString(4, lasku.getSaaja());
            stmt.setString(5, lasku.getYtunnus());
            stmt.setDouble(6, lasku.getAlvprosentti());
            stmt.setDouble(7, lasku.getMaara());
            stmt.setInt(8, lasku.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Lasku päivitetty ID:llä " + lasku.getId());
            } else {
                System.out.println("Ei laskua ID:llä " + lasku.getId());
            }
        } catch (SQLException e) {
            System.err.println("Virhe laskun päivityksessä: " + e.getMessage());
        }
    }

    /**
     * Poistaa laskun tiedot tietokannasta.
     * @param id poistettavan laskun ID
     */
    public void deleteLasku(int id) {
        String sql = "DELETE FROM LASKU WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Lasku poistettu ID:llä " + id);
            } else {
                System.out.println("Ei laskua ID:llä " + id);
            }
        } catch (SQLException e) {
            System.err.println("Virhe laskun poistamisessa: " + e.getMessage());
        }
    }
}
