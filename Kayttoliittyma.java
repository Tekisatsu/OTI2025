

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Luokka Kayttoliittyma, jossa määritellään ohjelmiston käyttöliittymän luominen. Luokka sisältää kirjautumisnäkymän ja
 * pääikkunan, jossa käyttäjä voi valita eri toimintoja valikkorakenteesta - varausten, mökki-, lasku-, asiakas-, osoite- ja raportointitietojen tarkastelun.
 * Luokka käyttää erillisiä luokkia (Varaus, Mökki, Asiakas, Lasku, Osoite ja Majoituksen raportointi) toimintojen toteuttamiseen.
 * Luokka toimii pääohjelmana ja käynnistää käyttöliittymän sekä mahdollistaa käyttäjän vuorovaikutuksen ohjelmiston eri osien kanssa.
 */
public class Kayttoliittyma extends Application {
    /**
     * Henkilökunta - olio, jota käytetään käyttäjän tunnistamiseen kirjautumisnäkymässä.
     */
    private Henkilokunta henkilokunta = new Henkilokunta();

    /**
     * Ohjelmiston pääikkuna, johon näkymät asetetaan.
     */
    private Stage primaryStage;

    /**
     * Ohjelmiston aloitusmetodi, joka kutsuu kirjautumisnäkymää.
     * @param primarystage Pääikkuna, johon näkymä rakennetaan
     */
    public void start(Stage primarystage) {
        this.primaryStage = primarystage;
        loggausNakyma();
    }
    void paanakyma(){
        VBox alku = new VBox(15);
        alku.setAlignment(Pos.CENTER);
        alku.setPadding(new Insets(20));

        Label kirjautumisTeksti = new Label("Kirjaudu sisään:");
        kirjautumisTeksti.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        HBox kayttajaTunnusBoksi = new HBox(10);
        kayttajaTunnusBoksi.setAlignment(Pos.CENTER);
        TextField kayttajaTunnusKentta = new TextField();
        kayttajaTunnusKentta.setPromptText("Käyttäjätunnus");
        kayttajaTunnusKentta.setPrefWidth(200);
        Button kayttajaPainike = new Button("Lisää käyttäjätunnus");
        Text tulos1Teksti = new Text();
        kayttajaTunnusBoksi.getChildren().addAll(kayttajaTunnusKentta, kayttajaPainike);

        HBox salasanaBoksi = new HBox(10);
        salasanaBoksi.setAlignment(Pos.CENTER);
        TextField salasanaKentta = new TextField();
        salasanaKentta.setPromptText("Salasana");
        salasanaKentta.setPrefWidth(200);
        Button salasanaPainike = new Button("Lisää salasana");
        Text tulos2Teksti = new Text();
        salasanaBoksi.getChildren().addAll(salasanaKentta, salasanaPainike);

        Button kirjauduPainike = new Button("Kirjaudu");

        //------------------------------------------------------------------------

        // Pääpaneeli ja valikko
        BorderPane paneeli = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menuValikko = new Menu("Valikko");

        // Valikkovaihtoehdot
        MenuItem menuVaraus = new MenuItem("Varaus");
        MenuItem menuMokki = new MenuItem("Mökit");
        MenuItem menuAsiakas = new MenuItem("Asiakas");
        MenuItem menuLasku = new MenuItem("Lasku");
        MenuItem menuOsoite = new MenuItem("Osoite");
        MenuItem menuRaportointi = new MenuItem("Majoituksen raportointi");

        menuValikko.getItems().addAll(menuVaraus, menuMokki, menuAsiakas, menuLasku, menuOsoite, menuRaportointi);
        menuBar.getMenus().addAll(menuValikko);
        paneeli.setTop(menuBar);

        //----------------------------------------------------------------------------------------
        //VARAUS -entiteetti

        // Tälle luotu oma metodi Varaus-luokassa

        //--------------------------------------------------------------------------------------------
        //MÖKIT -entiteetti

        // Tälle luotu oma metodi Mökki-luokassa

        //------------------------------------------------------------------------------------------
        //ASIAKAS -entiteetti

        // Tälle luotu oma metodi Asiakas-luokassa

        //------------------------------------------------------------------------------------
        //LASKU -entiteetti

        // Tälle luotu oma metodi Lasku-luokassa

        //------------------------------------------------------------------------------------
        //OSOITE -entiteetti

        // Tälle luotu oma metodi Osoite-luokassa

        //----------------------------------------------------------------------------------------
        //MAJOITUKSEN RAPORTOINTI -entiteetti

        // Tälle luotu oma metodi Raportti-luokassa

        //----------------------------------------------------------------------------------------

        /**
         * Toiminnallisuus, jolla valikosta vaihdetaan valikkokohteiden näkymät klikkaamalla.
         */
        Varaus varausnakyma = new Varaus();
        menuVaraus.setOnAction(e -> {
            // Näkymä vaihtuu Varaus - osioon
            paneeli.setCenter(varausnakyma.luoVarausNakyma());
        });
        Mokki mokkiNakyma = new Mokki();
        menuMokki.setOnAction(e -> {
            // Näkymä vaihtuu Mökki - osioon
            paneeli.setCenter(mokkiNakyma.luoMokkiNakyma());
        });
        Lasku laskuNakyma = new Lasku();
        menuLasku.setOnAction(e -> {
            // Näkymä vaihtuu Lasku - osioon
            paneeli.setCenter(laskuNakyma.luoLaskuNakyma());
        });
        Asiakas asiakasNakyma = new Asiakas();
        menuAsiakas.setOnAction(e -> {
            // Näkymä vaihtuu Asiakas - osioon
            paneeli.setCenter(asiakasNakyma.luoAsiakasNakyma());
        });
        Osoite osoiteNakyma = new Osoite();
        menuOsoite.setOnAction(e -> {
            // Näkymän vaihtuu Osoite - osioon
            paneeli.setCenter(osoiteNakyma.luoOsoiteNakyma());
        });
        Raportti raporttiNakyma = new Raportti();
        menuRaportointi.setOnAction(e -> {
            // Näkymä vaihtuu Raportti - osioon
            paneeli.setCenter(raporttiNakyma.luoRaportointiNakyma());
        });

        //---------------------------------------------------------------------------
        // Luodaan aloitusnäkymä, jossa käyttäjälle kerrotaan ohjeet ja valikkovaihtoehdot
        VBox aloitusVbox = new VBox();
        aloitusVbox.setStyle("-fx-background-color: lightgray;");
        aloitusVbox.setAlignment(Pos.CENTER_LEFT);
        aloitusVbox.setPadding(new Insets(30));
        aloitusVbox.setSpacing(20);

        aloitusVbox.setMaxWidth(650);
        aloitusVbox.setMaxHeight(650);
        aloitusVbox.setPrefHeight(650);
        aloitusVbox.setPrefWidth(650);

        GridPane aloitusGrid = new GridPane();
        aloitusGrid.setHgap(20);
        aloitusGrid.setVgap(20);
        aloitusGrid.setPadding(new Insets(20));
        aloitusGrid.setAlignment(Pos.CENTER);

        Label tervetuloaLabel = new Label("Tervetuloa mökkien varausjärjestelmään");
        tervetuloaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        aloitusGrid.add(tervetuloaLabel, 0, 0);

        Label tyhjaLabel = new Label(" ");
        aloitusGrid.add(tyhjaLabel, 0, 1);

        Label valitseLabel = new Label("         Valitse yläkulmasta haluamasi toiminto:");
        valitseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        aloitusGrid.add(valitseLabel, 0, 2);

        Label varausLabel = new Label("         Tee varaus");
        varausLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        aloitusGrid.add(varausLabel, 0, 3);

        Label mokitLabel = new Label("         Mökit");
        mokitLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        aloitusGrid.add(mokitLabel, 0, 4);

        Label asiakasLabel = new Label("         Asiakas");
        asiakasLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        aloitusGrid.add(asiakasLabel, 0, 5);

        Label laskuLabel = new Label("         Lasku");
        laskuLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        aloitusGrid.add(laskuLabel, 0, 6);

        Label osoiteLabel = new Label("         Osoite");
        osoiteLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        aloitusGrid.add(osoiteLabel, 0, 7);

        Label raportointiLabel = new Label("         Majoituksen raportointi");
        raportointiLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        aloitusGrid.add(raportointiLabel, 0, 8);

        aloitusVbox.getChildren().add(aloitusGrid);
        paneeli.setCenter(aloitusVbox);

        //--------------------------------------------------------------------

        // Scene, johon asetetetaan paneeli, koko ja otsikko
        Scene scene = new Scene(paneeli, 1100, 750);
        primaryStage.setTitle("Mökkien varaaminen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Ohjelman käynnistävä metodi.
     * @param args Käynnistää ohjelman
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Luodaan kirjautumisnäkymä, jossa käyttäjä voi syöttää käyttäjätunnuksen ja salasanan.
     * Mikäli käyttäjätunnus ja salasana on oikein, siirrytään käyttöliittymän päänäkymään.
     * Mikäli tiedot ovat virheelliset, näytetään virheilmoitus.
     */
    void loggausNakyma() {
        // Vbox, johon asetetaan kirjautumisnäkymän elementit
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

        // Virheilmoitus - kenttä, joka näytetään virheellisten tietojen syöttämisen jälkeen
        Text virheIlmoitus = new Text();
        virheIlmoitus.setFill(Color.RED);

        // Asetetaan toiminto käyttäjänimi- ja salasanakentille
        loggausPainike.setOnAction(e -> {
            String kayttajanimi = kayttajanimikentta.getText().trim();
            String salasana = salasanakentta.getText().trim();

            // Tarkistetaan, että kentät eivät ole tyhjiä
            if (kayttajanimi.isEmpty() || salasana.isEmpty()) {
                virheIlmoitus.setText("Syötä käyttäjätunnus ja salasana!");
                return;
            }

            // Tarkistetaan käyttäjänimi ja salasana. Jos kirjautuminen onnistuu, siirrytään pääsivulle,
            // muutoin näytetään virheilmoitus
            HenkilokuntaTiedot kayttajatiedot = henkilokunta.login(kayttajanimi, salasana);
            if (kayttajatiedot != null) {
                virheIlmoitus.setText("");
                paanakyma();
            } else {
                virheIlmoitus.setText("Virheellinen käyttäjätunnus tai salasana!");
                salasanakentta.clear();
            }
        });

        loggaus.getChildren().addAll(loggausLabel, kayttajanimikentta, salasanakentta, loggausPainike, virheIlmoitus);

        // Scene, johon asetetetaan kirjautumisnäkymä, määritellään ikkunan koko ja otsikko.
        Scene loggausScene = new Scene(loggaus, 400, 300);
        primaryStage.setTitle("Mökkikodit - Sisäänkirjautuminen");
        primaryStage.setScene(loggausScene);
        primaryStage.show();
    }
}
