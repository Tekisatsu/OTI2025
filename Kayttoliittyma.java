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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kayttoliittyma extends Application {
    TietokantaYhteysMokki tietokantaYhteysMokki = new TietokantaYhteysMokki();
    TietokantaYhteysAsiakas tietokantaYhteysAsiakas = new TietokantaYhteysAsiakas();
    TietokantaYhteysVaraus tietokantaYhteysVaraus = new TietokantaYhteysVaraus();
    TietokantaYhteysLasku tietokantaYhteysLasku = new TietokantaYhteysLasku();
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
        MenuItem menuOsoite = new MenuItem("Osoite");
        MenuItem menuRaportointi = new MenuItem("Majoituksen raportointi");

        menuValikko.getItems().addAll(menuVaraus, menuMokki, menuAsiakas, menuLasku, menuOsoite, menuRaportointi);
        menuBar.getMenus().addAll(menuValikko);
        paneeli.setTop(menuBar);

        //----------------------------------------------------------------------------------------
        //VARAUS -entiteetti
        HBox varausPohja = new HBox();
        varausPohja.setAlignment(Pos.CENTER);
        varausPohja.setStyle("-fx-background-color: lightgray;");
        varausPohja.setSpacing(50);
        varausPohja.setPadding(new Insets(30));
        varausPohja.setSpacing(20);

        varausPohja.setMaxWidth(800);
        varausPohja.setMaxHeight(800);
        varausPohja.setPrefHeight(800);
        varausPohja.setPrefWidth(800);

        VBox varausVbox = new VBox(20);
        varausVbox.setAlignment(Pos.TOP_LEFT);
        varausVbox.setPadding(new Insets(20));
        varausVbox.setPrefWidth(500);

        Label varausOtsikko = new Label("Tee varaus");
        varausOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        GridPane varausGrid = new GridPane();
        varausGrid.setHgap(15);
        varausGrid.setVgap(15);
        varausGrid.setPadding(new Insets(20));
        varausGrid.setAlignment(Pos.TOP_LEFT);

        varausGrid.add(new Label("ID:"), 0, 0);
        TextField varausIdKentta = new TextField();
        varausGrid.add(varausIdKentta, 1, 0);

        varausGrid.add(new Label("Alkamispäivämäärä:"), 0, 1);
        DatePicker alkamisPvmPicker = new DatePicker();
        varausGrid.add(alkamisPvmPicker, 1, 1);

        varausGrid.add(new Label("Päättymispäivämäärä:"), 0, 2);
        DatePicker paattymisPvmPicker = new DatePicker();
        varausGrid.add(paattymisPvmPicker, 1, 2);

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

        //buttonit
        HBox riviButtoneille1 = new HBox(30);
        Button btnLisaa1 = new Button("Lisää");
        Button btnPaivita1 = new Button("Päivitä");
        Button btnPoista1 = new Button("Poista");
        riviButtoneille1.getChildren().addAll(btnLisaa1, btnPaivita1, btnPoista1);
        riviButtoneille1.setAlignment(Pos.CENTER_LEFT);

        varausVbox.getChildren().addAll(varausOtsikko, varausGrid, riviButtoneille1);

        //Varaus TableView
        ObservableList<Varaus> varauksetList = FXCollections.observableArrayList(tietokantaYhteysVaraus.getAllVaraukset());
        ObservableList<VarausInfo> varausInfoList = getVarausInfo(varauksetList);
        TableView<VarausInfo> varausTable = new TableView<>(varausInfoList);
        varausTable.setPrefHeight(400);
        varausTable.setPrefWidth(200);
        varausTable.setStyle("-fx-background-color: white; -fx-border-color: gray;");
        TableColumn<VarausInfo, Integer> varausIdCol = new TableColumn<>("id");
        varausIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<VarausInfo, Asiakas> asiakasCol = new TableColumn<>("asiakas");
        asiakasCol.setCellValueFactory(new PropertyValueFactory<>("asiakas"));
        TableColumn<VarausInfo, Mokki> mokkiCol = new TableColumn<>("mökki");
        mokkiCol.setCellValueFactory(new PropertyValueFactory<>("mokki"));
        TableColumn<VarausInfo, Lasku> laskuCol = new TableColumn<>("lasku");
        laskuCol.setCellValueFactory(new PropertyValueFactory<>("lasku"));

        varausTable.getColumns().setAll(varausIdCol, asiakasCol,mokkiCol,laskuCol);
        varausVbox.getChildren().addAll(varausTable);

        varausPohja.getChildren().add(varausVbox);
        paneeli.setCenter(varausPohja);

        //tapahtumankäsittelijä lisää napille
        btnLisaa1.setOnAction(e -> {
            try {
                int id = Integer.parseInt(varausIdKentta.getText());
                LocalDate alkamispaivamaara = alkamisPvmPicker.getValue();
                LocalDate paattumispaivamaara = paattymisPvmPicker.getValue();
                int henkilomaara = Integer.parseInt(henkiloKentta.getText());
                int lasku_id = Integer.parseInt(varausLaskuIdKentta.getText());
                int mokki_id = Integer.parseInt(varausMokkiIdKentta.getText());
                int asiakas_id = Integer.parseInt(varausAsiakasIdKentta.getText());

                Varaus uusi = new Varaus(id, alkamispaivamaara, paattumispaivamaara, henkilomaara, lasku_id, mokki_id, asiakas_id);
                tietokantaYhteysVaraus.createVaraus(uusi);

                varausTable.setItems(FXCollections.observableArrayList(tietokantaYhteysVaraus.getAllVaraukset()));
                varausTable.getSelectionModel().clearSelection();
            } catch (NumberFormatException exception) {
                Alert virhe1 = new Alert(Alert.AlertType.ERROR, "Virhe lisättäessä varausta: " + exception.getMessage());
                virhe1.show();
            }
        });

        //tapahtumankäsittelijä päivitä napille
        btnPaivita1.setOnAction(e -> {
            Varaus valittu = varausTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                try {
                    valittu.setAlkamispaivamaara(alkamisPvmPicker.getValue());
                    valittu.setPaattumispaivamaara(paattymisPvmPicker.getValue());
                    valittu.setHenkilomaara(Integer.parseInt(henkiloKentta.getText()));
                    valittu.setLasku_id(Integer.parseInt(varausLaskuIdKentta.getText()));
                    valittu.setMokki_id(Integer.parseInt(varausMokkiIdKentta.getText()));
                    valittu.setAsiakas_id(Integer.parseInt(varausAsiakasIdKentta.getText()));
                    valittu.setId(Integer.parseInt(varausIdKentta.getText()));

                    tietokantaYhteysVaraus.updateVaraus(varaus);
                    varausTable.setItems(FXCollections.observableArrayList(tietokantaYhteysVaraus.getAllVaraukset()));
                    varausTable.getSelectionModel().clearSelection();
                } catch (NumberFormatException exception) {
                    Alert virhe2 = new Alert(Alert.AlertType.ERROR, "Virhe päivitettäessä varausta: " + exception.getMessage());
                    virhe2.show();
                }
            }
        });

        //tapahtumankäsittelijä poista napille
        btnPoista1.setOnAction(e -> {
            Varaus valittu = varausTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                try {
                    tietokantaYhteysVaraus.deleteVaraus(valittu.getId());
                    varausTable.setItems(FXCollections.observableArrayList(tietokantaYhteysVaraus.getAllVaraukset()));
                    varausTable.getSelectionModel().clearSelection();

                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Virhe poistettaessa varausta: " + ex.getMessage()).show();
                }
            }
        });

        //--------------------------------------------------------------------------------------------
        //MÖKIT -entiteetti
        HBox mokkiPohja = new HBox();
        mokkiPohja.setAlignment(Pos.CENTER);
        mokkiPohja.setStyle("-fx-background-color: lightgray;");
        mokkiPohja.setPadding(new Insets(30));

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
        Button btnLisaa2 = new Button("Lisää");
        Button btnPaivita2 = new Button("Päivitä");
        Button btnPoista2 = new Button("Poista");
        riviButtoneille2.getChildren().addAll(btnLisaa2, btnPaivita2, btnPoista2);
        riviButtoneille2.setAlignment(Pos.CENTER_LEFT);

        mokkiVbox.getChildren().addAll(mokitOtsikko, mokkiGrid, riviButtoneille2);

        ObservableList<Mokki> mokit = FXCollections.observableArrayList(tietokantaYhteysMokki.readAllMokit());
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

        //tapahtumankäsittelijä lisää napille
        btnLisaa2.setOnAction(ActionEvent -> {
            try {
                int id = Integer.parseInt(mokkiIdKentta.getText());
                String name = mokkiNimiKentta.getText();
                String tila = mokkiTilaKentta.getText();
                double vuokrahinta = Double.parseDouble(vuokrahintaKentta.getText());
                //haetaan osoite ID:n perusteella
                int osoiteId = Integer.parseInt(osoiteIdKentta.getText());
                Osoite osoite = yhteys.getOsoite(osoiteId);

                //olio johon kenttien arvot asetetaan
                Mokki mokki = new Mokki(id, name, tila, vuokrahinta, osoite);

                TietokantaYhteysMokki yhteys = new TietokantaYhteysMokki();
                yhteys.createMokki(mokki);

                mokkiTable.setItems(FXCollections.observableArrayList(yhteys.readAllMokit()));
                mokkiTable.getSelectionModel().clearSelection();
            } catch (Exception ex) {
                System.err.println("Virhe lisättäessä: " + ex.getMessage());
            }
        });

        //tapahtumankäsittelijä päivitä napille
        btnPaivita2.setOnAction(e -> {
            Mokki valittu = mokkiTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                try {
                    valittu.setId(Integer.parseInt(mokkiIdKentta.getText()));
                    valittu.setName(mokkiNimiKentta.getText());
                    valittu.setTila(mokkiTilaKentta.getText());
                    valittu.setVuokrahinta(Double.parseDouble(vuokrahintaKentta.getText()));
                    int osoiteId = Integer.parseInt(osoiteIdKentta.getText());
                    valittu.setOsoiteId(osoiteId);

                    TietokantaYhteysMokki yhteys = new TietokantaYhteysMokki();
                    yhteys.updateMokki(valittu);

                    mokkiTable.setItems(FXCollections.observableArrayList(yhteys.readAllMokit()));
                    mokkiTable.getSelectionModel().clearSelection();

                } catch (Exception ex) {
                    System.err.println("Virhe päivitettäessä: " + ex.getMessage());
                }
            }
        });

        //tapahtumankäsittelijä poista napille
        btnPoista2.setOnAction(e -> {
            Mokki valittu = mokkiTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                yhteys.deleteMokki(valittu.getId());
                mokkiTable.setItems(FXCollections.observableArrayList(yhteys.readAllMokit()));
                mokkiTable.getSelectionModel().clearSelection();
            }
        });

        //------------------------------------------------------------------------------------------
        //ASIAKAS -entiteetti

        // Tälle luotu oma metodi alempana

        //------------------------------------------------------------------------------------
        //LASKU -entiteetti

        // Tälle luotu oma metodi alempana

        //------------------------------------------------------------------------------------
        //OSOITE -entiteetti

        // Tälle luotu oma metodi alempana

        //----------------------------------------------------------------------------------------
        //MAJOITUKSEN RAPORTOINTI -entiteetti

        // Tälle luotu oma metodi alempana

        //----------------------------------------------------------------------------------------
        //vaihdetaan näkymiä menubarista klikkaamalla
        menuVaraus.setOnAction(e -> {
            paneeli.setCenter(varausVbox);
        });
        menuMokki.setOnAction(e -> {
            paneeli.setCenter(mokkiVbox);
        });
        menuLasku.setOnAction(e -> {
            paneeli.setCenter(luoLaskuNakyma());
        });
        menuAsiakas.setOnAction(e -> {
            paneeli.setCenter(luoAsiakasNakyma());
        });
        menuOsoite.setOnAction(e -> {
            paneeli.setCenter(luoOsoiteNakyma());
        });
        menuRaportointi.setOnAction(e -> {
            paneeli.setCenter(luoRaportointiNakyma());
        });

        //---------------------------------------------------------------------------
        //aloitusnäkymä
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

        Scene scene = new Scene(paneeli, 1100, 750);
        primarystage.setTitle("Mökkien varaaminen");
        primarystage.setScene(scene);
        primarystage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Asiakasnäkymän luonti
    public VBox luoAsiakasNakyma() {

        VBox asiakasVbox = new VBox(20);
        asiakasVbox.setStyle("-fx-background-color: lightgray;");
        asiakasVbox.setAlignment(Pos.CENTER_LEFT);
        asiakasVbox.setPadding(new Insets(30));

        Label asiakasOtsikko = new Label("Asiakas");
        asiakasOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Tekstikenttien luonti
        GridPane asiakasGrid = new GridPane();
        asiakasGrid.setHgap(15);
        asiakasGrid.setVgap(15);
        asiakasGrid.setPadding(new Insets(20));
        asiakasGrid.setAlignment(Pos.TOP_LEFT);

        asiakasGrid.add(new Label("Nimi:"), 0, 1);
        TextField asiakasNimiKentta = new TextField();
        asiakasGrid.add(asiakasNimiKentta, 1, 1);

        asiakasGrid.add(new Label("Sähköposti:"), 0, 2);
        TextField sahkopostiKentta = new TextField();
        asiakasGrid.add(sahkopostiKentta, 1, 2);

        asiakasGrid.add(new Label("Puhelinnumero:"), 0, 3);
        TextField puhelinnumeroKentta = new TextField();
        asiakasGrid.add(puhelinnumeroKentta, 1, 3);

        // Osoitteiden näyttäminen
        asiakasGrid.add(new Label("Osoite"), 0, 4);
        ComboBox<Osoite> osoiteComboBox = new ComboBox<>();
        TietokantaYhteysOsoite yhteysOsoite = new TietokantaYhteysOsoite();
        List<Osoite> osoitteet = yhteysOsoite.getAllOsoitteet();
        osoitteet.sort((Comparator.comparing(Osoite::getKatuosoite))); // Järjestetään katuosoitteen mukaan
        osoiteComboBox.setItems(FXCollections.observableArrayList(osoitteet));
        asiakasGrid.add(osoiteComboBox, 1, 4);

        // Miltä valikko näyttää sisältäpäin
        osoiteComboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Osoite item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getKatuosoite() + ", " + item.getKaupunki() + ", " + item.getMaa() + ", " + item.getZip());
            }
        });
        // Miltä valikko näyttää ulkoapäin
        osoiteComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Osoite item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getKatuosoite() + ", " + item.getKaupunki() + ", " + item.getMaa() + ", " + item.getZip());
            }
        });

        // Painikkeet lisäämiselle, päivittämiselle, ja poistamiselle
        HBox riviButtoneille3 = new HBox(30);
        Button btnLisaa3 = new Button("Lisää");
        Button btnPaivita3 = new Button("Päivitä");
        Button btnPoista3 = new Button("Poista");
        btnPaivita3.setVisible(false);
        btnPoista3.setVisible(false);
        riviButtoneille3.getChildren().addAll(btnLisaa3, btnPaivita3, btnPoista3);
        riviButtoneille3.setAlignment(Pos.CENTER_LEFT);

        // Taulukko aikaisempien asiakkaiden tarkastelulle
        TableView<Asiakas> asiakasTable = new TableView<>();
        asiakasTable.setPrefSize(700, 400);
        asiakasTable.setStyle("-fx-border-color: gray;");

        // Taulukon sarakkeeet
        TableColumn<Asiakas, Integer> asiakasIdCol = new TableColumn<>("ID");
        asiakasIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Asiakas, String> asiakasNimiCol = new TableColumn<>("Nimi");
        asiakasNimiCol.setCellValueFactory(new PropertyValueFactory<>("nimi"));
        TableColumn<Asiakas, String> asiakasSpostiCol = new TableColumn<>("Sähköposti");
        asiakasSpostiCol.setCellValueFactory(new PropertyValueFactory<>("sahkoposti"));
        TableColumn<Asiakas, String> puhCol = new TableColumn<>("Puhelinnumero");
        puhCol.setCellValueFactory(new PropertyValueFactory<>("puhelinnumero"));
        TableColumn<Asiakas, String> asiakasOsoiteCol = new TableColumn<>("Osoite");
        asiakasOsoiteCol.setCellValueFactory(new PropertyValueFactory<>("osoite"));

        asiakasTable.getColumns().setAll(asiakasIdCol, asiakasNimiCol, asiakasSpostiCol, puhCol, asiakasOsoiteCol);

        // Täytetään taulukko tietokannan avulla aina kun näkymä alustetaan
        ObservableList<Asiakas> asiakasData = FXCollections.observableArrayList(new TietokantaYhteysAsiakas().getAllAsiakkaat());
        asiakasTable.setItems(asiakasData);

        // Korostamalla asiakkaita saadaan täytettyä tekstikentät valmiiksi
        asiakasTable.getSelectionModel().selectedItemProperty().addListener((obs, vanha, uusi) -> {
            boolean valittu = uusi != null;
            btnPaivita3.setVisible(valittu);
            btnPoista3.setVisible(valittu);

            if (valittu) {
                asiakasNimiKentta.setText(uusi.getNimi());
                sahkopostiKentta.setText(uusi.getSahkoposti());
                puhelinnumeroKentta.setText(uusi.getPuhelinnumero());
                osoiteComboBox.setValue(uusi.getOsoite());
            }
        });

        // Klikkaus taulukon ulkopuolella tyhjentää valinnan ja kentät
        asiakasVbox.setOnMouseClicked(event -> {

            // Jos klikattu ei ollut taulukko tai mikään sen lapsi
            if (!asiakasTable.equals(event.getTarget()) && !asiakasTable.isHover()) {
                // Tyhjennetään valinta
                asiakasTable.getSelectionModel().clearSelection();

                // Tyhjennetään kentät
                asiakasNimiKentta.clear();
                sahkopostiKentta.clear();
                puhelinnumeroKentta.clear();
                osoiteComboBox.setValue(null);
            }
        });

        // Asiakkaiden lisääminen taulukkoon
        btnLisaa3.setOnAction(e -> {
            try {
                TietokantaYhteysAsiakas yhteysAsiakas = new TietokantaYhteysAsiakas();

                String nimi = asiakasNimiKentta.getText();
                String sahkoposti = sahkopostiKentta.getText();
                String puhelinnumero = puhelinnumeroKentta.getText();
                Osoite osoite = osoiteComboBox.getValue();
                Asiakas asiakas = new Asiakas(0, nimi, sahkoposti, puhelinnumero, osoite);

                yhteysAsiakas.createAsiakas(asiakas);

                asiakasTable.setItems(FXCollections.observableArrayList(yhteysAsiakas.getAllAsiakkaat()));

            } catch (Exception ex) {
                System.err.println("Virhe lisättäessä: " + ex.getMessage());
            }
        });

        // Asiakkaan päivittäminen
        btnPaivita3.setOnAction(e -> {
            Asiakas valittu = asiakasTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                try {
                    valittu.setNimi(asiakasNimiKentta.getText());
                    valittu.setSahkoposti(sahkopostiKentta.getText());
                    valittu.setPuhelinnumero(puhelinnumeroKentta.getText());
                    valittu.setOsoite(osoiteComboBox.getValue());

                    TietokantaYhteysAsiakas yhteysAsiakas = new TietokantaYhteysAsiakas();
                    yhteysAsiakas.updateAsiakas(valittu);

                    asiakasTable.setItems(FXCollections.observableArrayList(yhteysAsiakas.getAllAsiakkaat()));
                } catch (Exception ex) {
                    System.err.println("Virhe päivityksessä: " + ex.getMessage());
                }
            }
        });

        // Asiakkaan poistaminen taulukosta
        btnPoista3.setOnAction(e -> {
            Asiakas valittu = asiakasTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                TietokantaYhteysAsiakas yhteysAsiakas = new TietokantaYhteysAsiakas();
                yhteysAsiakas.deleteAsiakas(valittu.getId());

                asiakasTable.setItems(FXCollections.observableArrayList(yhteysAsiakas.getAllAsiakkaat()));

                // Tyhjennetään kentät
                asiakasNimiKentta.clear();
                sahkopostiKentta.clear();
                puhelinnumeroKentta.clear();
                osoiteComboBox.setValue(null);
            }
        });

        asiakasVbox.getChildren().addAll(asiakasOtsikko, asiakasGrid, riviButtoneille3, asiakasTable);
        return asiakasVbox;
    }

    // laskunäkymän luonti
    public VBox luoLaskuNakyma() {

        VBox laskuVbox = new VBox(20);
        laskuVbox.setStyle("-fx-background-color: lightgray;");
        laskuVbox.setAlignment(Pos.CENTER_LEFT);
        laskuVbox.setPadding(new Insets(30));

        Label laskuOtsikko = new Label("Lasku");
        laskuOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Luodaan ja asetetaan tekstikentät
        GridPane laskuGrid = new GridPane();
        laskuGrid.setHgap(20);
        laskuGrid.setVgap(20);
        laskuGrid.setPadding(new Insets(20));
        laskuGrid.setAlignment(Pos.CENTER_LEFT);

        laskuGrid.add(new Label("Saaja:"), 0, 0);
        TextField saajaKentta = new TextField();
        laskuGrid.add(saajaKentta, 1, 0);

        laskuGrid.add(new Label("Maksaja:"), 0, 1);
        TextField maksajaKentta = new TextField();
        laskuGrid.add(maksajaKentta, 1, 1);

        laskuGrid.add(new Label("Määrä:"), 0, 2);
        TextField maaraKentta = new TextField();
        laskuGrid.add(maaraKentta, 1, 2);

        laskuGrid.add(new Label("Viitenumero:"), 2, 0);
        TextField viitenumeroKentta = new TextField();
        laskuGrid.add(viitenumeroKentta, 3, 0);

        laskuGrid.add(new Label("Eräpäivä:"), 2, 1);
        DatePicker erapaivaKentta = new DatePicker();
        laskuGrid.add(erapaivaKentta, 3, 1);

        laskuGrid.add(new Label("Y-tunnus:"), 2, 2);
        TextField ytunnusKentta = new TextField();
        laskuGrid.add(ytunnusKentta, 3, 2);

        laskuGrid.add(new Label("ALV-prosentti:"), 2, 3);
        TextField alvKentta = new TextField();
        laskuGrid.add(alvKentta, 3, 3);

        // Painikkeet lisäämiselle, päivittämiselle, ja poistamiselle
        HBox riviButtoneille4 = new HBox(30);
        Button btnLisaa4 = new Button("Lisää");
        Button btnPaivita4 = new Button("Päivitä");
        Button btnPoista4 = new Button("Poista");
        btnPaivita4.setVisible(false);
        btnPoista4.setVisible(false);
        riviButtoneille4.getChildren().addAll(btnLisaa4, btnPaivita4, btnPoista4);
        riviButtoneille4.setAlignment(Pos.CENTER_LEFT);

        // Taulukko aikaisempien laskujen tarkastelulle
        TableView<Lasku> laskuTable = new TableView<>();
        laskuTable.setPrefSize(700, 400);
        laskuTable.setStyle("-fx-border-color: gray;");

        // Taulukon sarakkeet
        TableColumn<Lasku, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Lasku, String> viiteCol = new TableColumn<>("Viitenumero");
        viiteCol.setCellValueFactory(new PropertyValueFactory<>("viitenumero"));

        TableColumn<Lasku, LocalDate> eraCol = new TableColumn<>("Eräpäivä");
        eraCol.setCellValueFactory(new PropertyValueFactory<>("erapaiva"));

        TableColumn<Lasku, String> maksajaCol = new TableColumn<>("Maksaja");
        maksajaCol.setCellValueFactory(new PropertyValueFactory<>("maksaja"));

        TableColumn<Lasku, String> saajaCol = new TableColumn<>("Saaja");
        saajaCol.setCellValueFactory(new PropertyValueFactory<>("saaja"));

        TableColumn<Lasku, String> ytunnusCol = new TableColumn<>("Y-tunnus");
        ytunnusCol.setCellValueFactory(new PropertyValueFactory<>("ytunnus"));

        TableColumn<Lasku, Double> prosenttiCol = new TableColumn<>("Alennusveroprosentti");
        prosenttiCol.setCellValueFactory(new PropertyValueFactory<>("alvprosentti"));

        TableColumn<Lasku, Double> maaraCol = new TableColumn<>("Määrä");
        maaraCol.setCellValueFactory(new PropertyValueFactory<>("maara"));

        laskuTable.getColumns().addAll(idCol, viiteCol, eraCol, maksajaCol, saajaCol, ytunnusCol, prosenttiCol, maaraCol);

        // Täytetään taulukko tietokannan avulla aina kun näkymä alustetaan
        ObservableList<Lasku> laskuData = FXCollections.observableArrayList(new TietokantaYhteysLasku().getAllLaskut());
        laskuTable.setItems(laskuData);

        // Korostamalla laskuja saadaan täytettyä tekstikentät valmiiksi
        laskuTable.getSelectionModel().selectedItemProperty().addListener((obs, vanha, uusi) -> {
            boolean valittu = uusi != null;
            btnPaivita4.setVisible(valittu);
            btnPoista4.setVisible(valittu);

            if (valittu) {
                viitenumeroKentta.setText(uusi.getViitenumero());
                erapaivaKentta.setValue(uusi.getErapaiva());
                maksajaKentta.setText(uusi.getMaksaja());
                saajaKentta.setText(uusi.getSaaja());
                ytunnusKentta.setText(uusi.getYtunnus());
                alvKentta.setText(String.valueOf(uusi.getAlvprosentti()));
                maaraKentta.setText(String.valueOf(uusi.getMaara()));
            }
        });

        // Klikkaus taulukon ulkopuolella tyhjentää valinnan ja kentät
        laskuVbox.setOnMouseClicked(event -> {
            // Jos klikattu ei ollut taulukko tai mikään sen lapsi
            if (!laskuTable.equals(event.getTarget()) && !laskuTable.isHover()) {
                // Tyhjennetään valinta
                laskuTable.getSelectionModel().clearSelection();

                // Tyhjennetään kentät
                viitenumeroKentta.clear();
                erapaivaKentta.setValue(null);
                maksajaKentta.clear();
                saajaKentta.clear();
                ytunnusKentta.clear();
                alvKentta.clear();
                maaraKentta.clear();
            }
        });

        // Tapahtumankäsittelijä lisäys buttonille
        btnLisaa4.setOnAction(e -> {
            try {
                String viitenumero = viitenumeroKentta.getText();
                LocalDate erapaiva = erapaivaKentta.getValue();
                String maksaja = maksajaKentta.getText();
                String saaja = saajaKentta.getText();
                String ytunnus = ytunnusKentta.getText();
                double alv = Double.parseDouble(alvKentta.getText());
                double maara = Double.parseDouble(maaraKentta.getText());

                Lasku uusi = new Lasku(0, viitenumero, erapaiva, maksaja, saaja, ytunnus,
                        alv, maara);

                TietokantaYhteysLasku yhteys = new TietokantaYhteysLasku();
                yhteys.createLasku(uusi);

                laskuTable.setItems(FXCollections.observableArrayList(yhteys.getAllLaskut()));
                laskuTable.getSelectionModel().clearSelection();

            } catch (Exception ex) {
                System.err.println("Virhe lisättäessä: " + ex.getMessage());
            }
        });

        // Laskun päivittäminen
        btnPaivita4.setOnAction(e -> {
            Lasku valittu = laskuTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                try {
                    valittu.setViitenumero(viitenumeroKentta.getText());
                    valittu.setErapaiva(erapaivaKentta.getValue());
                    valittu.setMaksaja(maksajaKentta.getText());
                    valittu.setSaaja(saajaKentta.getText());
                    valittu.setYtunnus(ytunnusKentta.getText());
                    valittu.setAlvprosentti(Double.parseDouble(alvKentta.getText()));
                    valittu.setMaara(Double.parseDouble(maaraKentta.getText()));

                    TietokantaYhteysLasku yhteys = new TietokantaYhteysLasku();
                    yhteys.updateLasku(valittu);

                    laskuTable.setItems(FXCollections.observableArrayList(yhteys.getAllLaskut()));
                    laskuTable.getSelectionModel().clearSelection();
                } catch (Exception ex) {
                    System.err.println("Virhe päivityksessä: " + ex.getMessage());
                }
            }
        });

        // Laskun poistaminen taulukosta
        btnPoista4.setOnAction(e -> {
            Lasku valittu = laskuTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                TietokantaYhteysLasku yhteys = new TietokantaYhteysLasku();
                yhteys.deleteLasku(valittu.getId());

                laskuTable.setItems(FXCollections.observableArrayList(yhteys.getAllLaskut()));
                laskuTable.getSelectionModel().clearSelection();

                // Tyhjennetään kentät
                viitenumeroKentta.clear();
                erapaivaKentta.setValue(null);
                maksajaKentta.clear();
                saajaKentta.clear();
                ytunnusKentta.clear();
                alvKentta.clear();
                maaraKentta.clear();
            }
        });

        laskuVbox.getChildren().addAll(laskuOtsikko, laskuGrid, riviButtoneille4, laskuTable);
        return laskuVbox;
    }

    // Osoitenäkymän luonti
    public VBox luoOsoiteNakyma() {

        VBox osoiteVbox = new VBox(20);
        osoiteVbox.setStyle("-fx-background-color: lightgray;");
        osoiteVbox.setAlignment(Pos.CENTER_LEFT);
        osoiteVbox.setPadding(new Insets(30));

        Label osoiteOtsikko = new Label("Osoite");
        osoiteOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Luodaan ja asetetaan tekstikentät
        GridPane osoiteGrid = new GridPane();
        osoiteGrid.setHgap(20);
        osoiteGrid.setVgap(20);
        osoiteGrid.setPadding(new Insets(20));
        osoiteGrid.setAlignment(Pos.CENTER_LEFT);

        osoiteGrid.add(new Label("Katuosoite:"), 0, 0);
        TextField katuKentta = new TextField();
        osoiteGrid.add(katuKentta, 1, 0);

        osoiteGrid.add(new Label("Kaupunki:"), 0, 1);
        TextField kaupunkiKentta = new TextField();
        osoiteGrid.add(kaupunkiKentta, 1, 1);

        osoiteGrid.add(new Label("Maa:"), 0, 2);
        TextField maaKentta = new TextField();
        osoiteGrid.add(maaKentta, 1, 2);

        osoiteGrid.add(new Label("Postinumero:"), 2, 0);
        TextField postinumeroKentta = new TextField();
        osoiteGrid.add(postinumeroKentta, 3, 0);

        // Painikkeet lisäämiselle, päivittämiselle, ja poistamiselle
        HBox riviButtoneille5 = new HBox(30);
        Button btnLisaa5 = new Button("Lisää");
        Button btnPaivita5 = new Button("Päivitä");
        Button btnPoista5 = new Button("Poista");
        btnPaivita5.setVisible(false);
        btnPoista5.setVisible(false);
        riviButtoneille5.getChildren().addAll(btnLisaa5, btnPaivita5, btnPoista5);
        riviButtoneille5.setAlignment(Pos.CENTER_LEFT);

        // Taulukko aikaisempien osoitteiden tarkastelulle
        TableView<Osoite> osoiteTable = new TableView<>();
        osoiteTable.setPrefSize(700, 400);
        osoiteTable.setStyle("-fx-border-color: gray;");

        // Taulukon sarakkeet
        TableColumn<Osoite, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Osoite, String> katuCol = new TableColumn<>("Katuosoite");
        katuCol.setCellValueFactory(new PropertyValueFactory<>("katuosoite"));

        TableColumn<Osoite, String> kaupunkiCol = new TableColumn<>("Kaupunki");
        kaupunkiCol.setCellValueFactory(new PropertyValueFactory<>("kaupunki"));

        TableColumn<Osoite, String> maaCol = new TableColumn<>("Maa");
        maaCol.setCellValueFactory(new PropertyValueFactory<>("maa"));

        TableColumn<Osoite, Integer> zipCol = new TableColumn<>("Postinumero");
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));

        osoiteTable.getColumns().addAll(idCol, katuCol, kaupunkiCol, maaCol, zipCol);

        // Täytetään taulukko tietokannan avulla aina kun näkymä alustetaan
        ObservableList<Osoite> osoiteData = FXCollections.observableArrayList(new TietokantaYhteysOsoite().getAllOsoitteet());
        osoiteTable.setItems(osoiteData);

        // Korostamalla osoitteita saadaan täytettyä tekstikentät valmiiksi
        osoiteTable.getSelectionModel().selectedItemProperty().addListener((obs, vanha, uusi) -> {
            boolean valittu = uusi != null;
            btnPaivita5.setVisible(valittu);
            btnPoista5.setVisible(valittu);

            if (valittu) {
                katuKentta.setText(uusi.getKatuosoite());
                kaupunkiKentta.setText(uusi.getKaupunki());
                maaKentta.setText(uusi.getMaa());
                postinumeroKentta.setText(String.valueOf(uusi.getZip()));
            }
        });

        // Klikkaus taulukon ulkopuolella tyhjentää valinnan ja kentät
        osoiteVbox.setOnMouseClicked(event -> {
            // Jos klikattu ei ollut taulukko tai mikään sen lapsi
            if (!osoiteTable.equals(event.getTarget()) && !osoiteTable.isHover()) {
                // Tyhjennetään valinta
                osoiteTable.getSelectionModel().clearSelection();

                // Tyhjennetään kentät
                katuKentta.clear();
                kaupunkiKentta.clear();
                maaKentta.clear();
                postinumeroKentta.clear();
            }
        });

        // Tapahtumankäsittelijä lisäys buttonille
        btnLisaa5.setOnAction(e -> {
            try {
                String katuosoite = katuKentta.getText();
                String kaupunki = kaupunkiKentta.getText();
                String maa = maaKentta.getText();
                int postinumero = Integer.parseInt(postinumeroKentta.getText());

                Osoite uusi = new Osoite(0, katuosoite, kaupunki, maa, postinumero);

                TietokantaYhteysOsoite yhteys = new TietokantaYhteysOsoite();
                yhteys.createOsoite(uusi);

                osoiteTable.setItems(FXCollections.observableArrayList(yhteys.getAllOsoitteet()));
                osoiteTable.getSelectionModel().clearSelection();

            } catch (Exception ex) {
                System.err.println("Virhe lisättäessä: " + ex.getMessage());
            }
        });

        // Osoitteen päivittäminen
        btnPaivita5.setOnAction(e -> {
            Osoite valittu = osoiteTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                try {
                    valittu.setKatuosoite(katuKentta.getText());
                    valittu.setKaupunki(kaupunkiKentta.getText());
                    valittu.setMaa(maaKentta.getText());
                    valittu.setZip(Integer.parseInt(postinumeroKentta.getText()));

                    TietokantaYhteysOsoite yhteys = new TietokantaYhteysOsoite();
                    yhteys.updateOsoite(valittu);

                    osoiteTable.setItems(FXCollections.observableArrayList(yhteys.getAllOsoitteet()));
                    osoiteTable.getSelectionModel().clearSelection();
                } catch (Exception ex) {
                    System.err.println("Virhe päivityksessä: " + ex.getMessage());
                }
            }
        });

        // Laskun poistaminen taulukosta
        btnPoista5.setOnAction(e -> {
            Osoite valittu = osoiteTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                TietokantaYhteysOsoite yhteys = new TietokantaYhteysOsoite();
                yhteys.deleteOsoite(valittu.getId());

                osoiteTable.setItems(FXCollections.observableArrayList(yhteys.getAllOsoitteet()));
                osoiteTable.getSelectionModel().clearSelection();

                // Tyhjennetään kentät
                katuKentta.clear();
                kaupunkiKentta.clear();
                maaKentta.clear();
                postinumeroKentta.clear();
            }
        });

        osoiteVbox.getChildren().addAll(osoiteOtsikko, osoiteGrid, riviButtoneille5, osoiteTable);
        return osoiteVbox;
    }

    // raportointinäkymän luonti
    public VBox luoRaportointiNakyma() {

        VBox raporttiVBox = new VBox(20);
        raporttiVBox.setStyle("-fx-background-color: lightgray;");
        raporttiVBox.setAlignment(Pos.CENTER_LEFT);
        raporttiVBox.setPadding(new Insets(30));

        Label otsikko = new Label("Majoituksen raportointi");
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // painikkeet päivämäärän valinnalle
        HBox pvmValinta = new HBox(10);
        Label alkuPvmLabel = new Label("Alkupäivämäärä");
        DatePicker alkuPvmPicker = new DatePicker();
        Label loppuPvmLabel = new Label("Loppupäivämäärä");
        DatePicker loppuPvmPicker = new DatePicker();
        pvmValinta.getChildren().addAll(alkuPvmLabel, alkuPvmPicker, loppuPvmLabel, loppuPvmPicker);
        pvmValinta.setAlignment(Pos.CENTER_LEFT);

        // painike raporttiin lisäämiselle
        Button lisaaBtn = new Button("Lisää raporttiin");

        // painike raportista poistamiseen
        Button poistoBtn = new Button("Poista raportista");
        poistoBtn.setVisible(false);

        HBox painikeHBox = new HBox(40);
        painikeHBox.getChildren().addAll(lisaaBtn, poistoBtn);

        // taulukko raportin tarkastelulle
        TableView<Raportti> raporttiTaulukko = new TableView<>();
        raporttiTaulukko.setPrefSize(700, 400);
        raporttiTaulukko.setStyle("-fx-border-color: gray;");

        // taulukon sarakkeet
        TableColumn<Raportti, LocalDate> alkuCol = new TableColumn<>("Alku");
        alkuCol.setCellValueFactory(new PropertyValueFactory<>("alkuPvm"));

        TableColumn<Raportti, LocalDate> loppuCol = new TableColumn<>("Loppu");
        loppuCol.setCellValueFactory(new PropertyValueFactory<>("loppuPvm"));

        TableColumn<Raportti, Integer> varausCol = new TableColumn<>("Varauksia");
        varausCol.setCellValueFactory(new PropertyValueFactory<>("varaustenMaara"));

        TableColumn<Raportti, BigDecimal> laskutusCol = new TableColumn<>("Laskutus (€)");
        laskutusCol.setCellValueFactory(new PropertyValueFactory<>("kokonaislaskutus"));

        TableColumn<Raportti, String> mokkiCol = new TableColumn<>("Suosituin mökki");
        mokkiCol.setCellValueFactory(new PropertyValueFactory<>("suosituinMokki"));

        TableColumn<Raportti, Integer> kestoCol = new TableColumn<>("Keskikesto (pv)");
        kestoCol.setCellValueFactory(new PropertyValueFactory<>("keskimKesto"));

        TableColumn<Raportti, Integer> asiakasCol = new TableColumn<>("Asiakkaita");
        asiakasCol.setCellValueFactory(new PropertyValueFactory<>("asiakasmaara"));

        raporttiTaulukko.getColumns().addAll(alkuCol, loppuCol, varausCol, laskutusCol, mokkiCol, kestoCol, asiakasCol);

        ObservableList<Raportti> raportit = FXCollections.observableArrayList();

        // luetaan CSV aina kun näkymä alustetaan
        raportit.setAll(RaporttiCSV.lueRaportitCSV("raportti.csv"));
        raporttiTaulukko.setItems(raportit);

        // Korostamalla raportteja saadaan täytettyä kentät valmiiksi
        raporttiTaulukko.getSelectionModel().selectedItemProperty().addListener((obs, vanha, uusi) -> {
            boolean valittu = uusi != null;
            poistoBtn.setVisible(valittu);

            if (valittu) {
                alkuPvmPicker.setValue(uusi.getAlkuPvm());
                loppuPvmPicker.setValue(uusi.getLoppuPvm());
            }
        });

        // Klikkaus taulukon ulkopuolella tyhjentää valinnan ja kentät
        raporttiVBox.setOnMouseClicked(event -> {
            // Jos klikattu ei ollut taulukko tai mikään sen lapsi
            if (!raporttiTaulukko.equals(event.getTarget()) && !raporttiTaulukko.isHover()) {
                // Tyhjennetään valinta
                raporttiTaulukko.getSelectionModel().clearSelection();

                // Tyhjennetään kentät
                alkuPvmPicker.setValue(null);
                loppuPvmPicker.setValue(null);
            }
        });

        // lisääminen taulukkoon
        lisaaBtn.setOnAction(e -> {
            LocalDate alkuPaivamaara = alkuPvmPicker.getValue();
            LocalDate loppuPaivamaara = loppuPvmPicker.getValue();

            if (alkuPaivamaara != null && loppuPaivamaara != null && !loppuPaivamaara.isBefore(alkuPaivamaara)) {
                TietokantaYhteysRaportti yhteys = new TietokantaYhteysRaportti();
                Raportti raportti = new Raportti(
                        alkuPaivamaara,
                        loppuPaivamaara,
                        yhteys.haeVaraustenMaara(alkuPaivamaara, loppuPaivamaara),
                        yhteys.haeKokonaislaskutus(alkuPaivamaara, loppuPaivamaara),
                        yhteys.haeSuosituinMokki(alkuPaivamaara, loppuPaivamaara),
                        yhteys.haeKeskimaarainenKesto(alkuPaivamaara, loppuPaivamaara),
                        yhteys.haeAsiakasmaara(alkuPaivamaara, loppuPaivamaara)
                );

                raportit.add(raportti);

                // päivitetään raportti CSV:hen joka lisäyksen jälkeen
                RaporttiCSV.kirjoitaRaportitCSV(new ArrayList<>(raportit), "raportti.csv");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Valitse kelvollinen päivämääräväli.");
                alert.show();
            }
        });

        // poistaminen taulukosta
        poistoBtn.setOnAction(e -> {
            Raportti valittu = raporttiTaulukko.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                raportit.remove(valittu); // poistaa ObservableList:stä
                // päivitetään raportti CSV:hen jokaisen onnistuneen poiston jälkeen
                RaporttiCSV.kirjoitaRaportitCSV(raportit, "raportti.csv");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Valitse osio, jonka haluat poistaa.");
                alert.show();
            }
        });

        raporttiVBox.getChildren().addAll(otsikko, pvmValinta, painikeHBox, raporttiTaulukko);
        return raporttiVBox;
    }
    
    public ObservableList<VarausInfo> getVarausInfo(ObservableList<Varaus> list) {
        ObservableList<VarausInfo> varausInfo = FXCollections.observableArrayList();
        for (Varaus varaus : list) {
            Asiakas asi = tietokantaYhteysAsiakas.getAsiakastiedot(varaus.asiakas_id);
            Mokki mok = tietokantaYhteysMokki.readMokki(varaus.mokki_id);
            Lasku las = tietokantaYhteysLasku.getLaskuID(varaus.lasku_id);
            varausInfo.add(new VarausInfo(varaus.id, asi, mok, las, varaus.henkilomaara, varaus.alkamispaivamaara,varaus.paattumispaivamaara));
        }
        return varausInfo;
    }
    
    public boolean alreadyExists(Object o) {
        if (o instanceof Asiakas) {
            List<Asiakas> a = tietokantaYhteysAsiakas.getAllAsiakkaat();
            for (Asiakas asiakas : a) {
                if (asiakas.equals(o)){
                    return true;
                }
            }
            return false;
        }
        if (o instanceof Mokki) {
            List<Mokki> m = tietokantaYhteysMokki.readAllMokit();
            for (Mokki mokki : m) {
                if (mokki.equals(o)){
                    return true;
                }
            }
            return false;
        }
        if (o instanceof Lasku) {
            List<Lasku> l = tietokantaYhteysLasku.getAllLaskut();
            for (Lasku lasku : l) {
                if (lasku.equals(o)){
                    return true;
                }
            }
            return false;
        }
        if (o instanceof Varaus) {
            List<Varaus> v = tietokantaYhteysVaraus.getAllVaraukset();
            for (Varaus varaus : v) {
                if (varaus.equals(o)){
                    return true;
                }
            }
            return false;
        }
        if (o instanceof VarausInfo) {
            ObservableList<Varaus> varauksetList = FXCollections.observableArrayList(tietokantaYhteysVaraus.getAllVaraukset());
            ObservableList<VarausInfo> v = getVarausInfo(varauksetList);
            for (VarausInfo varausInfo : v) {
                if (varausInfo.equals(o)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
