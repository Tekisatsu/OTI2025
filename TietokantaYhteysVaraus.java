import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TietokantaYhteysVaraus {
    private String url = "jdbc:mysql://localhost:3306/mokki_varaus?useSSL=false";
    private String kayttajanimi = "tietokantaKäyttäjäNimi";
    private String salasana = "tietokantaSalasana";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    // luo varaus
    public void createVaraus(Varaus varaus) {
        String sql = "INSERT INTO VARAUS (Lasku_ID, Mokki_ID, Asiakas_ID, Aloituspaivamaara, Paattymispaivamaara) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, varaus.getLasku_id());
            stmt.setInt(2, varaus.getMokki_id());
            stmt.setInt(3, varaus.getAsiakas_id());
            stmt.setDate(4, Date.valueOf(varaus.getAlkamispaivamaara()));
            stmt.setDate(5, Date.valueOf(varaus.getPaattumispaivamaara()));
            stmt.executeUpdate();
            System.out.println("Varaus luotu mökki ID:lle " + varaus.getMokki_id());
        } catch (SQLException e) {
            System.err.println("Virhe varauksen luonnissa: " + e.getMessage());
        }
    }

    // lue varaus
    public Varaus getVaraus(int id) {
        String sql = "SELECT * FROM VARAUS WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Varaus(
                        rs.getInt("ID"),
                        rs.getInt("Asiakas_ID"),
                        rs.getInt("Mokki_ID"),
                        rs.getInt("Lasku_ID"),
                        rs.getDate("Aloituspaivamaara").toLocalDate(),
                        rs.getDate("Paattymispaivamaara").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.err.println("Virhe varauksen lukemisessa: " + e.getMessage());
        }
        return null;
    }

    // lue kaikki varaukset
    public List<Varaus> getAllVaraukset() {
        List<Varaus> varaukset = new ArrayList<>();
        String sql = "SELECT * FROM VARAUS";
        try (Connection yhteys = getConnection(); Statement stmt = yhteys.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Varaus varaus = new Varaus(
                        rs.getInt("ID"),
                        rs.getInt("Asiakas_ID"),
                        rs.getInt("Mokki_ID"),
                        rs.getInt("Lasku_ID"),
                        rs.getDate("Aloituspaivamaara").toLocalDate(),
                        rs.getDate("Paattymispaivamaara").toLocalDate()
                );
                varaukset.add(varaus);
            }
        } catch (SQLException e) {
            System.err.println("Virhe varausten lukemisessa: " + e.getMessage());
        }
        return varaukset;
    }

    // päivitä varaus
    public void updateVaraus(Varaus varaus) {
        String sql = "UPDATE VARAUS SET Lasku_ID = ?, Mokki_ID = ?, Asiakas_ID = ?, Aloituspaivamaara = ?, " +
                "Paattymispaivamaara = ? WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, varaus.getLasku_id());
            stmt.setInt(2, varaus.getMokki_id());
            stmt.setInt(3, varaus.getAsiakas_id());
            stmt.setDate(4, Date.valueOf(varaus.getAlkamispaivamaara()));
            stmt.setDate(5, Date.valueOf(varaus.getPaattumispaivamaara()));
            stmt.setInt(6, varaus.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Varaus päivitetty ID:lle " + varaus.getId());
            } else {
                System.out.println("Ei varausta ID:llä " + varaus.getId());
            }
        } catch (SQLException e) {
            System.err.println("Virhe varauksen päivityksessä: " + e.getMessage());
        }
    }

    // poista varaus
    public void deleteVaraus(int id) {
        String sql = "DELETE FROM VARAUS WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Varaus poistettu ID:llä " + id);
            } else {
                System.out.println("Ei varausta ID:llä " + id);
            }
        } catch (SQLException e) {
            System.err.println("Virhe varauksen poistamisessa: " + e.getMessage());
        }
    }
}