import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Kauttoliittyma extends Application {
    public void start(Stage primarystage) {
        //POP UP IKKUNA
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
        //MENUBAR
        //pohjalle Borderpane
        BorderPane paneeli = new BorderPane();

        //menubar
        MenuBar menuBar = new MenuBar();
        //päävalikko
        Menu menuValikko = new Menu("Valikko");
        //valikon alla olevat
        MenuItem menuVaraus = new MenuItem("Tee varaus");
        MenuItem menuMokki = new MenuItem("Mökit");
        MenuItem menuAsiakas = new MenuItem("Asiakas");
        MenuItem menuLasku = new MenuItem("Lasku");
        MenuItem menuRaportointi = new MenuItem("Majoituksen raportointi");

        menuValikko.getItems().addAll(menuVaraus, menuMokki, menuAsiakas, menuLasku, menuRaportointi);
        menuBar.getMenus().addAll(menuValikko);
        paneeli.setTop(menuBar);

        //----------------------------------------------------------------------------------------
        //VARAUS -entiteetti

        VBox varausVbox = new VBox(30);
        varausVbox.setStyle("-fx-background-color: lightgray;");
        varausVbox.setAlignment(Pos.CENTER);
        varausVbox.setPadding(new Insets(30));
        varausVbox.setSpacing(20);

        varausVbox.setMaxWidth(650);
        varausVbox.setMaxHeight(650);
        varausVbox.setPrefHeight(650);
        varausVbox.setPrefWidth(650);

        Label varausOtsikko = new Label("Tee varaus");
        varausOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 16));

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

        //tallenna ja peruuta - nappi
        HBox riviButtoneille1 = new HBox(30);

        Button btnTallenna1 = new Button("Tallenna");
        Button btnPeruuta1 = new Button("Peruuta");
        riviButtoneille1.getChildren().addAll(btnTallenna1, btnPeruuta1);
        riviButtoneille1.setAlignment(Pos.CENTER);
        varausVbox.getChildren().addAll(varausOtsikko, varausGrid, riviButtoneille1);

        //Varaus TableView
        ObservableList<Varaus> varaukset = FXCollections.observableArrayList();
        TableView<Varaus> varausTable = new TableView<>(varaukset);
        varausTable.setPrefHeight(400);
        varausTable.setPrefWidth(200);
        varausTable.setStyle("-fx-background-color: white; -fx-border-color: gray;");
        TableColumn<Varaus, Integer> varausIdCol = new TableColumn<>("id");
        varausIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Varaus, Integer> asiakasIdCol = new TableColumn<>("asiakas");
        asiakasIdCol.setCellValueFactory(new PropertyValueFactory<>("asiakas_id"));
        TableColumn<Varaus, Integer> mokkiIdCol = new TableColumn<>("mökki");
        mokkiIdCol.setCellValueFactory(new PropertyValueFactory<>("mökki_id"));
        TableColumn<Varaus, Integer> laskuIdCol = new TableColumn<>("lasku");
        laskuIdCol.setCellValueFactory(new PropertyValueFactory<>("lasku_id"));

        varausTable.getColumns().setAll(varausIdCol, asiakasIdCol,mokkiIdCol,laskuIdCol);
        varausVbox.getChildren().addAll(varausTable);
        paneeli.setCenter(varausVbox);

        //tapahtumankäsittelijä tallenna buttonille
        btnTallenna1.setOnAction(e -> {
            try {
                int id = Integer.parseInt(varausIdKentta.getText());
                int asiakas_id = Integer.parseInt(varausAsiakasIdKentta.getText());
                int mokki_id = Integer.parseInt(varausMokkiIdKentta.getText());
                int lasku_id = Integer.parseInt(varausLaskuIdKentta.getText());

                //alkamis- ja päättymispvm, henkilömäärä?

            } catch (NumberFormatException exception) {
                throw new RuntimeException(exception);
            }
        });


        //--------------------------------------------------------------------------------------------
        //MÖKIT -entiteetti
        HBox mokkiPohja = new HBox();
        mokkiPohja.setAlignment(Pos.CENTER);
        mokkiPohja.setStyle("-fx-background-color: lightgray;");
        mokkiPohja.setSpacing(50);
        //mokkiPohja.setPadding(new Insets(30));
        //mokkiPohja.setSpacing(20);

        //mokkiPohja.setMaxWidth(800);
        //mokkiPohja.setMaxHeight(800);
        //mokkiPohja.setPrefHeight(800);
        //mokkiPohja.setPrefWidth(800);

        VBox mokkiVbox = new VBox(20);
        mokkiVbox.setAlignment(Pos.TOP_LEFT);
        mokkiVbox.setPadding(new Insets(20));
        mokkiVbox.setPrefWidth(500);

        Label mokitOtsikko = new Label("Mökit");
        mokitOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        GridPane mokkiGrid = new GridPane();
        mokkiGrid.setHgap(15);
        mokkiGrid.setVgap(15);
        mokkiGrid.setPadding(new Insets(20));
        mokkiGrid.setAlignment(Pos.TOP_LEFT);

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


        //tallenna ja peruuta -nappi
        HBox riviButtoneille2 = new HBox(30);
        Button btnTallenna2 = new Button("Tallenna");
        Button btnPeruuta2 = new Button("Peruuta");
        riviButtoneille2.getChildren().addAll(btnTallenna2, btnPeruuta2);
        riviButtoneille2.setAlignment(Pos.CENTER_LEFT);

        // testidataa:
        // POISTA KUN EI TARVITA
        Osoite o1 = new Osoite("Testikatu 6", "Helsinki", "Suomi", 1000);
        Osoite o2 = new Osoite("Osoitetie 15", "Oulu", "Suomi", 4444);
        Osoite o3 = new Osoite("Kauppakatu 22", "Kuopio", "Suomi", 7050);
        Mokki m1 = new Mokki(1,"Testimökki",o1,"Tyhjä",60.0);
        Mokki m2 = new Mokki(2,"Tokamökki",o2,"Varattu",800.0);
        Mokki m3 = new Mokki(3,"Koomökki",o3,"Tyhjä",120.0);

        mokkiVbox.getChildren().addAll(mokitOtsikko,mokkiGrid,riviButtoneille2);

        ObservableList<Mokki> mokit = FXCollections.observableArrayList(m1,m2,m3);
        TableView<Mokki> mokkiTable = new TableView<>(mokit);
        mokkiTable.setPrefHeight(400);
        mokkiTable.setPrefWidth(200);
        mokkiTable.setStyle("-fx-background-color: white; -fx-border-color: gray;");
        TableColumn<Mokki, Integer> idCol = new TableColumn<>("id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Mokki, String> nameCol = new TableColumn<>("nimi");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Mokki, String> osoiteCol = new TableColumn<>("osoite");
        osoiteCol.setCellValueFactory(new PropertyValueFactory<>("osoite"));
        TableColumn<Mokki, String> vuokrahintaCol = new TableColumn<>("hinta");
        vuokrahintaCol.setCellValueFactory(new PropertyValueFactory<>("vuokrahinta"));

        mokkiTable.getColumns().setAll(idCol, nameCol,osoiteCol,vuokrahintaCol);

        mokkiVbox.getChildren().addAll(mokkiTable);
        mokkiPohja.getChildren().addAll(mokkiVbox);

        paneeli.setCenter(mokkiPohja);


        //tapahtumankäsittelijä tallenna buttonille
        btnTallenna2.setOnAction(ActionEvent -> {
            try {
                int id = Integer.parseInt(mokkiIdKentta.getText());
                String name = mokkiNimiKentta.getText();
                String tila = mokkiTilaKentta.getText();
                double vuokrahinta = Double.parseDouble(vuokrahintaKentta.getText());
                //osoite?

            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        });


        //------------------------------------------------------------------------------------------
        //ASIAKAS -entiteetti

        VBox asiakasVbox = new VBox();
        asiakasVbox.setStyle("-fx-background-color: lightgray;");
        asiakasVbox.setAlignment(Pos.CENTER);
        asiakasVbox.setPadding(new Insets(30));
        asiakasVbox.setSpacing(20);

        asiakasVbox.setMaxWidth(650);
        asiakasVbox.setMaxHeight(650);
        asiakasVbox.setPrefHeight(650);
        asiakasVbox.setPrefWidth(650);

        Label asiakasOtsikko = new Label("Asiakas");
        asiakasOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 16));

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

        //tallenna ja peruuta - nappi
        HBox riviButtoneille3 = new HBox(30);
        Button btnTallenna3 = new Button("Tallenna");
        Button btnPeruuta3 = new Button("Peruuta");
        riviButtoneille3.getChildren().addAll(btnTallenna3, btnPeruuta3);
        riviButtoneille3.setAlignment(Pos.CENTER);
        asiakasVbox.getChildren().addAll(asiakasOtsikko, asiakasGrid, riviButtoneille3);
        paneeli.setCenter(asiakasVbox);

        //tapahtumankäsittelijä tallenna buttonille
        btnTallenna3.setOnAction(ActionEvent -> {
            try {
                int id = Integer.parseInt(asiakasIdKentta.getText());
                String name = asiakasNimiKentta.getText();
                String sahkoposti = sahkopostiKentta.getText();
                //puhelinnumero pitää vaihtaa intiksi?
                //osoiteid
                //maksutiedot
                //varaukset

            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        });


        //------------------------------------------------------------------------------------
        //LASKU -entiteetti

        VBox laskuVbox = new VBox();
        laskuVbox.setStyle("-fx-background-color: lightgray;");
        laskuVbox.setAlignment(Pos.CENTER);
        laskuVbox.setPadding(new Insets(30));
        laskuVbox.setSpacing(20);

        laskuVbox.setMaxWidth(650);
        laskuVbox.setMaxHeight(650);
        laskuVbox.setPrefHeight(650);
        laskuVbox.setPrefWidth(650);

        Label laskuOtsikko = new Label("Laskut");
        laskuOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        GridPane laskuGrid = new GridPane();
        laskuGrid.setHgap(20);
        laskuGrid.setVgap(20);
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

        //tallenna ja peruuta nappi
        HBox riviButtoneille4 = new HBox(30);
        Button btnTallenna4 = new Button("Tallenna");
        Button btnPeruuta4 = new Button("Peruuta");
        riviButtoneille4.getChildren().addAll(btnTallenna4, btnPeruuta4);
        riviButtoneille4.setAlignment(Pos.CENTER);
        laskuVbox.getChildren().addAll(laskuOtsikko,laskuGrid, riviButtoneille4);
        paneeli.setCenter(laskuVbox);

        //tapahtumankäsittelijä tallenna buttonille
        btnTallenna4.setOnAction(ActionEvent -> {
            try {
                int id = Integer.parseInt(laskuIdKentta.getText());
                String saaja = saajaKentta.getText();
                String maksaja = maksajaKentta.getText();
                double maara = Double.parseDouble(maaraKentta.getText());
                int viitenumero = Integer.parseInt(viitenumeroKentta.getText());
                //eräpäivä
                //ytunnus intiksi?
                double alvprosentti = Double.parseDouble(alvKentta.getText());

            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        });


        //----------------------------------------------------------------------------------------
        //MAJOITUKSEN RAPORTOINTI -entiteetti


        //----------------------------------------------------------------------------------------
        //vaihdetaan näkymiä menubarista klikkaamalla
        menuVaraus.setOnAction(e -> {
            paneeli.setCenter(varausVbox);
        });
        menuMokki.setOnAction(e -> {
            paneeli.setCenter(mokkiVbox);
        });
        menuLasku.setOnAction(e -> {
            paneeli.setCenter(laskuVbox);
        });
        menuAsiakas.setOnAction(e -> {
            paneeli.setCenter(asiakasVbox);
        });

        //käyttöliittymän näyttö on aluksi tyhjä
        //Mitä tähän keksisi?
        paneeli.setCenter(null);

        Scene scene = new Scene(paneeli, 1100, 750);
        primarystage.setTitle("Mökkien varaaminen");
        primarystage.setScene(scene);
        primarystage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
