import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TietokantaYhteysLasku {
    private String url = "jdbc:mysql://localhost:3306/mokki_varaus?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    // luo lasku
    public void createLasku(Lasku lasku) {
        String sql = "INSERT INTO LASKU (Viitenumero, Erapaiva, Maksaja, Saaja, Ytunnus, ALV_prosentti, Maara) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, lasku.getViitenumero());
            stmt.setDate(2, new java.sql.Date(lasku.getErapaiva().getTime()));
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

    // lue lasku
    public Lasku getLaskuID(int id) {
        String sql = "SELECT * FROM LASKU WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Lasku(
                        String.valueOf(rs.getInt("ID")),
                        String.valueOf(rs.getInt("Viitenumero")),
                        rs.getDate("Erapaiva"),
                        rs.getString("Maksaja"),
                        rs.getString("Saaja"),
                        rs.getString("Ytunnus"),
                        String.valueOf(rs.getDouble("ALV_prosentti")),
                        String.valueOf(rs.getDouble("Maara"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Virhe laskun luennassa: " + e.getMessage());
        }
        return null;
    }

    // lue kaikki laskut
    public List<Lasku> getAllLaskut() {
        List<Lasku> laskut = new ArrayList<>();
        String sql = "SELECT * FROM LASKU";
        try (Connection yhteys = getConnection(); Statement stmt = yhteys.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Lasku lasku = new Lasku(
                        String.valueOf(rs.getInt("ID")),
                        String.valueOf(rs.getInt("Viitenumero")),
                        rs.getDate("Erapaiva"),
                        rs.getString("Maksaja"),
                        rs.getString("Saaja"),
                        rs.getString("Ytunnus"),
                        String.valueOf(rs.getDouble("ALV_prosentti")),
                        String.valueOf(rs.getDouble("Maara"))
                );
                laskut.add(lasku);
            }
        } catch (SQLException e) {
            System.err.println("Virhe laskujen luennassa: " + e.getMessage());
        }
        return laskut;
    }

    // päivitä lasku
    public void updateLasku(Lasku lasku) {
        String sql = "UPDATE LASKU SET Viitenumero = ?, Erapaiva = ?, Maksaja = ?, Saaja = ?, Ytunnus = ?, " +
                "ALV_prosentti = ?, Maara = ? WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, lasku.getViitenumero());
            stmt.setDate(2, new java.sql.Date(lasku.getErapaiva().getTime()));
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

    // poista lasku
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
