package org.example.demo14;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class Kayttoliittymaa_uusi extends Application {
    private Henkilokunta henkilokunta = new Henkilokunta();
    private BorderPane mainPane;
    private Scene mainScene;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loggausNakyma();
    }

    private void loggausNakyma() {
        VBox loggaus = new VBox(15);
        loggaus.setAlignment(Pos.CENTER);
        loggaus.setPadding(new Insets(20));
        loggaus.setStyle("-fx-background-color: #f4f4f4;");

        Label loggausLabel = new Label("Kirjaudu sisään");
        loggausLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TextField kayttajanimikentta = new TextField();
        kayttajanimikentta.setPromptText("Käyttäjätunnus");
        kayttajanimikentta.setPrefWidth(250);
        kayttajanimikentta.setMaxWidth(250);

        PasswordField salasanakentta = new PasswordField();
        salasanakentta.setPromptText("Salasana");
        salasanakentta.setPrefWidth(250);
        salasanakentta.setMaxWidth(250);

        Button loggausPainike = new Button("Kirjaudu");
        loggausPainike.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loggausPainike.setPrefWidth(150);

        Text virheIlmoitus = new Text();
        virheIlmoitus.setFill(Color.RED);

        loggausPainike.setOnAction(e -> {
            String kayttajanimi = kayttajanimikentta.getText().trim();
            String salasana = salasanakentta.getText().trim();

            if (kayttajanimi.isEmpty() || salasana.isEmpty()) {
                virheIlmoitus.setText("Syötä käyttäjätunnus ja salasana!");
                return;
            }

            HenkilokuntaTiedot kayttajatiedot = henkilokunta.login(kayttajanimi, salasana);
            if (kayttajatiedot != null) {
                virheIlmoitus.setText("");
                Paanakyma();
            } else {
                virheIlmoitus.setText("Virheellinen käyttäjätunnus tai salasana!");
                salasanakentta.clear();
            }
        });

        loggaus.getChildren().addAll(loggausLabel, kayttajanimikentta, salasanakentta, loggausPainike, virheIlmoitus);
        Scene loggausScene = new Scene(loggaus, 400, 300);
        primaryStage.setTitle("Mökkikodit - Sisäänkirjautuminen");
        primaryStage.setScene(loggausScene);
        primaryStage.show();
    }

    private void Paanakyma() {
        // MENUBAR
        mainPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menuValikko = new Menu("Valikko");
        MenuItem menuVaraus = new MenuItem("Tee varaus");
        MenuItem menuMokki = new MenuItem("Mökit");
        MenuItem menuAsiakas = new MenuItem("Asiakas");
        MenuItem menuLasku = new MenuItem("Lasku");
        MenuItem menuRaportointi = new MenuItem("Majoituksen raportointi");

        menuValikko.getItems().addAll(menuVaraus, menuMokki, menuAsiakas, menuLasku, menuRaportointi);
        menuBar.getMenus().addAll(menuValikko);
        mainPane.setTop(menuBar);

        // VARAUS -entiteetti
        VBox varausVbox = new VBox(30);
        varausVbox.setStyle("-fx-background-color: lightgray;");
        varausVbox.setAlignment(Pos.CENTER);
        varausVbox.setPadding(new Insets(30));
        varausVbox.setSpacing(20);
        varausVbox.setMaxWidth(650);
        varausVbox.setMaxHeight(650);
        varausVbox.setPrefHeight(650);
        varausVbox.setPrefWidth(650);

        GridPane varausGrid = new GridPane();
        varausGrid.setHgap(15);
        varausGrid.setVgap(15);
        varausGrid.setPadding(new Insets(20));
        varausGrid.setAlignment(Pos.CENTER);

        varausGrid.add(new Label("ID:"), 0, 0);
        TextField varausIdKentta = new TextField();
        varausGrid.add(varausIdKentta, 1, 0);

        varausGrid.add(new Label("Alkamispäivämäärä:"), 0, 1);
        TextField alkamisPvmKentta = new TextField();
        varausGrid.add(alkamisPvmKentta, 1, 1);

        varausGrid.add(new Label("Päättymispäivämäärä:"), 0, 2);
        TextField paattymisPvmKentta = new TextField();
        varausGrid.add(paattymisPvmKentta, 1, 2);

        varausGrid.add(new Label("Henkilömäärä:"), 0, 3);
        TextField henkiloKentta = new TextField();
        varausGrid.add(henkiloKentta, 1, 3);

        varausGrid.add(new Label("Lasku ID:"), 2, 1);
        TextField varausLaskuIdKentta = new TextField();
        varausGrid.add(varausLaskuIdKentta, 3, 1);

        varausGrid.add(new Label("Mökki ID:"), 2, 2);
        TextField varausMokkiIdKentta = new TextField();
        varausGrid.add(varausMokkiIdKentta, 3, 2);

        varausGrid.add(new Label("Asiakas ID:"), 2, 3);
        TextField varausAsiakasIdKentta = new TextField();
        varausGrid.add(varausAsiakasIdKentta, 3, 3);

        HBox riviButtoneille1 = new HBox(30);
        Button btnTallenna1 = new Button("Tallenna");
        Button btnPeruuta1 = new Button("Peruuta");
        riviButtoneille1.getChildren().addAll(btnTallenna1, btnPeruuta1);
        riviButtoneille1.setAlignment(Pos.CENTER);
        varausVbox.getChildren().addAll(varausGrid, riviButtoneille1);

        btnTallenna1.setOnAction(e -> {
            try {
                int id = Integer.parseInt(varausIdKentta.getText());
                int asiakas_id = Integer.parseInt(varausAsiakasIdKentta.getText());
                int mokki_id = Integer.parseInt(varausMokkiIdKentta.getText());
                int lasku_id = Integer.parseInt(varausLaskuIdKentta.getText());
            } catch (NumberFormatException exception) {
                throw new RuntimeException(exception);
            }
        });

        // MÖKIT -entiteetti
        VBox mokkiVbox = new VBox();
        mokkiVbox.setStyle("-fx-background-color: lightgray;");
        mokkiVbox.setAlignment(Pos.CENTER);
        mokkiVbox.setPadding(new Insets(30));
        mokkiVbox.setSpacing(20);
        mokkiVbox.setMaxWidth(650);
        mokkiVbox.setMaxHeight(650);
        mokkiVbox.setPrefHeight(650);
        mokkiVbox.setPrefWidth(650);

        GridPane mokkiGrid = new GridPane();
        mokkiGrid.setHgap(15);
        mokkiGrid.setVgap(15);
        mokkiGrid.setPadding(new Insets(20));
        mokkiGrid.setAlignment(Pos.CENTER);

        mokkiGrid.add(new Label("ID:"), 0, 0);
        TextField mokkiIdKentta = new TextField();
        mokkiGrid.add(mokkiIdKentta, 1, 0);

        mokkiGrid.add(new Label("Nimi:"), 0, 1);
        TextField mokkiNimiKentta = new TextField();
        mokkiGrid.add(mokkiNimiKentta, 1, 1);

        mokkiGrid.add(new Label("Tila:"), 0, 2);
        TextField mokkiTilaKentta = new TextField();
        mokkiGrid.add(mokkiTilaKentta, 1, 2);

        mokkiGrid.add(new Label("Vuokrahinta:"), 2, 1);
        TextField vuokrahintaKentta = new TextField();
        mokkiGrid.add(vuokrahintaKentta, 3, 1);

        mokkiGrid.add(new Label("Osoite ID:"), 2, 2);
        TextField osoiteIdKentta = new TextField();
        mokkiGrid.add(osoiteIdKentta, 3, 2);

        HBox riviButtoneille2 = new HBox(30);
        Button btnTallenna2 = new Button("Tallenna");
        Button btnPeruuta2 = new Button("Peruuta");
        riviButtoneille2.getChildren().addAll(btnTallenna2, btnPeruuta2);
        riviButtoneille2.setAlignment(Pos.CENTER);
        mokkiVbox.getChildren().addAll(mokkiGrid, riviButtoneille2);

        btnTallenna2.setOnAction(e -> {
            try {
                int id = Integer.parseInt(mokkiIdKentta.getText());
                String name = mokkiNimiKentta.getText();
                String tila = mokkiTilaKentta.getText();
                double vuokrahinta = Double.parseDouble(vuokrahintaKentta.getText());
            } catch (NumberFormatException ex) {
                throw new RuntimeException(ex);
            }
        });

        // ASIAKAS -entiteetti
        VBox asiakasVbox = new VBox();
        asiakasVbox.setStyle("-fx-background-color: lightgray;");
        asiakasVbox.setAlignment(Pos.CENTER);
        asiakasVbox.setPadding(new Insets(30));
        asiakasVbox.setSpacing(20);
        asiakasVbox.setMaxWidth(650);
        asiakasVbox.setMaxHeight(650);
        asiakasVbox.setPrefHeight(650);
        asiakasVbox.setPrefWidth(650);

        GridPane asiakasGrid = new GridPane();
        asiakasGrid.setHgap(15);
        asiakasGrid.setVgap(15);
        asiakasGrid.setPadding(new Insets(20));
        asiakasGrid.setAlignment(Pos.CENTER);

        asiakasGrid.add(new Label("ID:"), 0, 0);
        TextField asiakasIdKentta = new TextField();
        asiakasGrid.add(asiakasIdKentta, 1, 0);

        asiakasGrid.add(new Label("Nimi:"), 0, 1);
        TextField asiakasNimiKentta = new TextField();
        asiakasGrid.add(asiakasNimiKentta, 1, 1);

        asiakasGrid.add(new Label("Sähköposti:"), 0, 2);
        TextField sahkopostiKentta = new TextField();
        asiakasGrid.add(sahkopostiKentta, 1, 2);

        asiakasGrid.add(new Label("Puhelinnumero:"), 0, 3);
        TextField puhelinnumeroKentta = new TextField();
        asiakasGrid.add(puhelinnumeroKentta, 1, 3);

        asiakasGrid.add(new Label("Osoite ID:"), 2, 1);
        TextField asiakasOsoiteIdKentta = new TextField();
        asiakasGrid.add(asiakasOsoiteIdKentta, 3, 1);

        asiakasGrid.add(new Label("Maksutiedot ID:"), 2, 2);
        TextField maksutiedotIdKentta = new TextField();
        asiakasGrid.add(maksutiedotIdKentta, 3, 2);

        asiakasGrid.add(new Label("Varaukset:"), 2, 3);
        TextField varauksetKentta = new TextField();
        asiakasGrid.add(varauksetKentta, 3, 3);

        HBox riviButtoneille3 = new HBox(30);
        Button btnTallenna3 = new Button("Tallenna");
        Button btnPeruuta3 = new Button("Peruuta");
        riviButtoneille3.getChildren().addAll(btnTallenna3, btnPeruuta3);
        riviButtoneille3.setAlignment(Pos.CENTER);
        asiakasVbox.getChildren().addAll(asiakasGrid, riviButtoneille3);

        btnTallenna3.setOnAction(e -> {
            try {
                int id = Integer.parseInt(asiakasIdKentta.getText());
                String name = asiakasNimiKentta.getText();
                String sahkoposti = sahkopostiKentta.getText();
            } catch (NumberFormatException ex) {
                throw new RuntimeException(ex);
            }
        });

        // LASKU -entiteetti
        VBox laskuVbox = new VBox();
        laskuVbox.setStyle("-fx-background-color: lightgray;");
        laskuVbox.setAlignment(Pos.CENTER);
        laskuVbox.setPadding(new Insets(30));
        laskuVbox.setSpacing(20);
        laskuVbox.setMaxWidth(650);
        laskuVbox.setMaxHeight(650);
        laskuVbox.setPrefHeight(650);
        laskuVbox.setPrefWidth(650);

        GridPane laskuGrid = new GridPane();
        laskuGrid.setHgap(15);
        laskuGrid.setVgap(15);
        laskuGrid.setPadding(new Insets(20));
        laskuGrid.setAlignment(Pos.CENTER);

        laskuGrid.add(new Label("ID:"), 0, 0);
        TextField laskuIdKentta = new TextField();
        laskuGrid.add(laskuIdKentta, 1, 0);

        laskuGrid.add(new Label("Saaja:"), 0, 1);
        TextField saajaKentta = new TextField();
        laskuGrid.add(saajaKentta, 1, 1);

        laskuGrid.add(new Label("Maksaja:"), 0, 2);
        TextField maksajaKentta = new TextField();
        laskuGrid.add(maksajaKentta, 1, 2);

        laskuGrid.add(new Label("Määrä:"), 0, 3);
        TextField maaraKentta = new TextField();
        laskuGrid.add(maaraKentta, 1, 3);

        laskuGrid.add(new Label("Viitenumero:"), 2, 0);
        TextField viitenumeroKentta = new TextField();
        laskuGrid.add(viitenumeroKentta, 3, 0);

        laskuGrid.add(new Label("Eräpäivä:"), 2, 1);
        TextField erapaivaKentta = new TextField();
        laskuGrid.add(erapaivaKentta, 3, 1);

        laskuGrid.add(new Label("Y-tunnus:"), 2, 2);
        TextField ytunnusKentta = new TextField();
        laskuGrid.add(ytunnusKentta, 3, 2);

        laskuGrid.add(new Label("ALV-prosentti:"), 2, 3);
        TextField alvKentta = new TextField();
        laskuGrid.add(alvKentta, 3, 3);

        HBox riviButtoneille4 = new HBox(30);
        Button btnTallenna4 = new Button("Tallenna");
        Button btnPeruuta4 = new Button("Peruuta");
        riviButtoneille4.getChildren().addAll(btnTallenna4, btnPeruuta4);
        riviButtoneille4.setAlignment(Pos.CENTER);
        laskuVbox.getChildren().addAll(laskuGrid, riviButtoneille4);

        btnTallenna4.setOnAction(e -> {
            try {
                int id = Integer.parseInt(laskuIdKentta.getText());
                String saaja = saajaKentta.getText();
                String maksaja = maksajaKentta.getText();
                double maara = Double.parseDouble(maaraKentta.getText());
                int viitenumero = Integer.parseInt(viitenumeroKentta.getText());
                double alvprosentti = Double.parseDouble(alvKentta.getText());
            } catch (NumberFormatException ex) {
                throw new RuntimeException(ex);
            }
        });

        // vaihdetaan näkymiä menubarista klikkaamalla
        menuVaraus.setOnAction(e -> mainPane.setCenter(varausVbox));
        menuMokki.setOnAction(e -> mainPane.setCenter(mokkiVbox));
        menuAsiakas.setOnAction(e -> mainPane.setCenter(asiakasVbox));
        menuLasku.setOnAction(e -> mainPane.setCenter(laskuVbox));
        menuRaportointi.setOnAction(e -> mainPane.setCenter(null));

        mainPane.setCenter(null);
        mainScene = new Scene(mainPane, 800, 700);
        primaryStage.setTitle("Mökkien varaaminen");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

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

class Henkilokunta {
    private String url = "jdbc:mysql://localhost:3306/mokki_varaus?useSSL=false";
    private String kayttajanimi = "root";
    private String salasana = "tietokantaSalasana";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, kayttajanimi, salasana);
    }

    public HenkilokuntaTiedot login(String kayttajatunnus, String salasana) {
        String sql = "SELECT * FROM HENKILOKUNTA WHERE Kayttajatunnus = ? AND Salasana = ?";
        try (Connection yhteys = getConnection(); PreparedStatement stmt = yhteys.prepareStatement(sql)) {
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
}