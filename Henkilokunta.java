import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class HenkilokuntaTiedot {
    private int id;
    private String kayttajatunnus;
    private String salasana;
    private String kayttoikeus;

    public int getHenkilokuntaId() {
        return id;
    }

    public void setHenkilokuntaId(int id) {
        this.id = id;
    }

    public String getKayttajatunnus() {
        return kayttajatunnus;
    }

    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public String getKayttoikeus() {
        return kayttoikeus;
    }

    public void setKayttoikeus(String kayttoikeus) {
        this.kayttoikeus = kayttoikeus;
    }

    @Override
    public String toString() {
        return "HenkilokuntaTiedot{" +
                "id=" + id +
                ", kayttajatunnus='" + kayttajatunnus + '\'' +
                ", kayttoikeus='" + kayttoikeus + '\'' +
                '}';
    }
}
/**
 * Luokka, jonka avulla hallinnoidaan henkilökunnan tietoja tietokannassa.
 */
public class Henkilokunta {
    private String url = "jdbc:mysql://localhost:3306/mokkikodit?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    /**
     * Luo yhteyden mokkikodit.sql tietokantaan.
     * @return tietokantayhteys
     * @throws SQLException jos yhteyden luominen epäonnistuu
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    /**
     * Kirjaa henkilökuntaan kuuluva sisään käyttäjätunnuksella ja salasanalla.
     * @param kayttajatunnus käyttäjätunnus
     * @param salasana salasana
     * @return henkilökuntaan kuuluvan tiedot, jos kirjautuminen onnistuu, muutoin null
     */
    public HenkilokuntaTiedot login(String kayttajatunnus, String salasana) {
        String sql = "SELECT * FROM HENKILOKUNTA WHERE Kayttajatunnus = ? AND Salasana = ?";
        try (Connection yhteys= getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, kayttajatunnus);
            stmt.setString(2, salasana);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HenkilokuntaTiedot henkilokunta = new HenkilokuntaTiedot();
                henkilokunta.setHenkilokuntaId(rs.getInt("ID"));
                henkilokunta.setKayttajatunnus(rs.getString("Kayttajatunnus"));
                henkilokunta.setSalasana(rs.getString("Salasana"));
                henkilokunta.setKayttoikeus(rs.getString("Kayttoikeus"));
                return henkilokunta;
            }
        } catch (SQLException e) {
            System.err.println("Virhe kirjautumisen yhteydessä: " + e.getMessage());
        }
        return null;
    }

    /**
     * Luo uuden henkilökuntaan kuuluvan tietokantaan.
     * @param henkilokunta henkilökuntalaisen tiedot
     */
    public void createHenkilokunta(HenkilokuntaTiedot henkilokunta) {
        String sql = "INSERT INTO HENKILOKUNTA (Kayttajatunnus, Salasana, Kayttoikeus) VALUES (?, ?, ?)";
        try (Connection yhteys= getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, henkilokunta.getKayttajatunnus());
            stmt.setString(2, henkilokunta.getSalasana());
            stmt.setString(3, henkilokunta.getKayttoikeus());
            stmt.executeUpdate();
            System.out.println("Henkilö lisätty tunnuksella " + henkilokunta.getKayttajatunnus());
        } catch (SQLException e) {
            System.err.println("Virhe henkilön lisäämisessa: " + e.getMessage());
        }
    }

    /**
     * Lukee henkilökuntalaisen tiedot ID:n perusteella.
     * @param id henkilökuntaan kuuluvan ID
     * @return henkilökuntalaisen tiedot, jos löytyvät, muuten null
     */
    public HenkilokuntaTiedot getHenkilokunta(int id) {
        String sql = "SELECT * FROM HENKILOKUNTA WHERE ID = ?";
        try (Connection yhteys= getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HenkilokuntaTiedot henkilokunta = new HenkilokuntaTiedot();
                henkilokunta.setHenkilokuntaId(rs.getInt("ID"));
                henkilokunta.setKayttajatunnus(rs.getString("Kayttajatunnus"));
                henkilokunta.setSalasana(rs.getString("Salasana"));
                henkilokunta.setKayttoikeus(rs.getString("Kayttoikeus"));
                return henkilokunta;
            }
        } catch (SQLException e) {
            System.err.println("Virhe henkilökuntatietojen luennassa: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lukee kaikkien henkilökuntaan kuuluvien tiedot tietokannasta.
     * @return lista henkilökunnan jäsenten tiedoista
     */
    public List<HenkilokuntaTiedot> getAllHenkilokunta() {
        List<HenkilokuntaTiedot> henkilokunnat = new ArrayList<>();
        String sql = "SELECT * FROM HENKILOKUNTA";
        try (Connection yhteys= getConnection(); Statement stmt = yhteys.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HenkilokuntaTiedot henkilokunta = new HenkilokuntaTiedot();
                henkilokunta.setHenkilokuntaId(rs.getInt("ID"));
                henkilokunta.setKayttajatunnus(rs.getString("Kayttajatunnus"));
                henkilokunta.setSalasana(rs.getString("Salasana"));
                henkilokunta.setKayttoikeus(rs.getString("Kayttoikeus"));
                henkilokunnat.add(henkilokunta);
            }
        } catch (SQLException e) {
            System.err.println("Virhe henkilökuntatietojen luennassa: " + e.getMessage());
        }
        return henkilokunnat;
    }

    /**
     * Päivittää henkilökuntalaisen tiedot tietokannassa.
     * @param henkilokunta päivitettävät henkilökuntalaisen tiedot
     */
    public void updateHenkilokunta(HenkilokuntaTiedot henkilokunta) {
        String sql = "UPDATE HENKILOKUNTA SET Kayttajatunnus = ?, Salasana = ?, Kayttoikeus = ? WHERE ID = ?";
        try (Connection yhteys= getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setString(1, henkilokunta.getKayttajatunnus());
            stmt.setString(2, henkilokunta.getSalasana());
            stmt.setString(3, henkilokunta.getKayttoikeus());
            stmt.setInt(4, henkilokunta.getHenkilokuntaId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Henkilökuntalaisen tiedot päivitetty tunnuksella " + henkilokunta.getKayttajatunnus());
            } else {
                System.out.println("Ei henkilökuntaan kuuluvaa ID:llä " + henkilokunta.getHenkilokuntaId());
            }
        } catch (SQLException e) {
            System.err.println("Virhe tietojen päivityksessä: " + e.getMessage());
        }
    }

    /**
     * Poistaa henkilökuntaan kuuluvan henkilön tiedot tietokannasta.
     * @param id poistettavan henkilökuntalaisen ID
     */
    public void deleteHenkilokunta(int id) {
        String sql = "DELETE FROM HENKILOKUNTA WHERE ID = ?";
        try (Connection yhteys= getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Henkilökuntalainen poistettu ID:llä " + id);
            } else {
                System.out.println("Ei henkilökuntaan kuuluvaa ID:llä " + id);
            }
        } catch (SQLException e) {
            System.err.println("Virhe henkilökuntaan kuuluvan poistossa: " + e.getMessage());
        }
    }
}
