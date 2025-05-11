import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TietokantaYhteysVaraus {
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

    // lue asiakastiedot
    public Asiakas getAsiakastiedotTK(int id) {
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

    // lue mökki ID:n perusteella
    public Mokki getMokkiTK(int id) {
        String sql = "SELECT * FROM MOKKI WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Mokki mokki = new Mokki();
                mokki.setId(rs.getInt("ID"));
                mokki.setOsoite(getOsoiteTK(rs.getInt("Osoite_id")));
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

    // lue lasku
    public Lasku getLaskuTK(int id) {
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

    // luo varaus
    public void createVaraus(Varaus varaus) {
        String sql = "INSERT INTO VARAUS (Lasku_ID, Mokki_ID, Asiakas_ID, Henkilomaara, Aloituspaivamaara, Paattymispaivamaara) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, varaus.getLasku().getId());
            stmt.setInt(2, varaus.getMokki().getId());
            stmt.setInt(3, varaus.getAsiakas().getId());
            stmt.setInt(4, varaus.getHenkilomaara());
            stmt.setDate(5, Date.valueOf(varaus.getAloituspaivamaara()));
            stmt.setDate(6, Date.valueOf(varaus.getPaattymispaivamaara()));
            stmt.executeUpdate();
            System.out.println("Varaus luotu mökille " + varaus.getMokki().getNimi());
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
                Asiakas asiakas = getAsiakastiedotTK(rs.getInt("Asiakas_ID"));
                Mokki mokki = getMokkiTK(rs.getInt("Mokki_ID"));
                Lasku lasku = getLaskuTK(rs.getInt("Lasku_ID"));
                return new Varaus(
                        rs.getInt("ID"),
                        asiakas,
                        mokki,
                        lasku,
                        rs.getInt("Henkilomaara"),
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
                Asiakas asiakas = getAsiakastiedotTK(rs.getInt("Asiakas_ID"));
                Mokki mokki = getMokkiTK(rs.getInt("Mokki_ID"));
                Lasku lasku = getLaskuTK(rs.getInt("Lasku_ID"));
                Varaus varaus = new Varaus(
                        rs.getInt("ID"),
                        asiakas,
                        mokki,
                        lasku,
                        rs.getInt("Henkilomaara"),
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
        String sql = "UPDATE VARAUS SET Lasku_ID = ?, Mokki_ID = ?, Asiakas_ID = ?, Henkilomaara = ?, Aloituspaivamaara = ?, " +
                "Paattymispaivamaara = ? WHERE ID = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, varaus.getLasku().getId());
            stmt.setInt(2, varaus.getMokki().getId());
            stmt.setInt(3, varaus.getAsiakas().getId());
            stmt.setInt(4, varaus.getHenkilomaara());
            stmt.setDate(5, Date.valueOf(varaus.getAloituspaivamaara()));
            stmt.setDate(6, Date.valueOf(varaus.getPaattymispaivamaara()));
            stmt.setInt(7, varaus.getId());
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
