import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Raportti-luokka mallintaa raporttia, johon voidaan tallentaa päivämääräväli, varausmäärä,
 * kokonaislaskutus, päivämäärävälin suosituin mökki, varausten keskimääräinen kesto, ja
 * rekisteröityjen asiakkaiden määrä.
 */
public class Raportti {

    /**
     * Tarkasteltavan välin alkamispäivämäärä.
     */
    private LocalDate alkuPvm;
    /**
     * Tarkasteltavan välin päättymispäivämäärä.
     */
    private LocalDate loppuPvm;
    /**
     * Varausmäärä tarkasteltavalla välillä.
     */
    private int varaustenMaara;
    /**
     * Kokonaislaskutus tarkasteltavalla välillä.
     */
    private BigDecimal kokonaislaskutus;
    /**
     * Suosituin mökki tarkasteltavalla välillä.
     */
    private String suosituinMokki;
    /**
     * Varausten keskimääräinen kesto päivinä tarkasteltavalla välillä.
     */
    private int keskimKesto;
    /**
     * Varauksiin liitettyjen uniikkien asiakkaiden määrä tarkasteltavalla välillä.
     */
    private int asiakasmaara;

    public Raportti() {
    }

    public Raportti(LocalDate alkuPvm, LocalDate loppuPvm, int varaustenMaara,
                    BigDecimal kokonaislaskutus, String suosituinMokki,
                    int keskimKesto, int asiakasmaara) {
        this.alkuPvm = alkuPvm;
        this.loppuPvm = loppuPvm;
        this.varaustenMaara = varaustenMaara;
        this.kokonaislaskutus = kokonaislaskutus;
        this.suosituinMokki = suosituinMokki;
        this.keskimKesto = keskimKesto;
        this.asiakasmaara = asiakasmaara;
    }

    public LocalDate getAlkuPvm() {
        return alkuPvm;
    }

    public LocalDate getLoppuPvm() {
        return loppuPvm;
    }

    public int getVaraustenMaara() {
        return varaustenMaara;
    }

    public BigDecimal getKokonaislaskutus() {
        return kokonaislaskutus;
    }

    public String getSuosituinMokki() {
        return suosituinMokki;
    }

    public int getKeskimKesto() {
        return keskimKesto;
    }

    public int getAsiakasmaara() {
        return asiakasmaara;
    }

    public String toString() {
        return String.format(
                "Raportti ajalta %s - %s\n" +
                        "Varausten määrä: %d\n" +
                        "Kokonaislaskutus: %.2f€\n" +
                        "Suosituin mökki: %s\n" +
                        "Keskimääräinen varauksen kesto: %d päivää\n" +
                        "Asiakasmäärä: %d\n",
                alkuPvm, loppuPvm, varaustenMaara, kokonaislaskutus,
                suosituinMokki, keskimKesto, asiakasmaara);
    }

    /**
     * Metodi raportointinäkymän luomiseen käyttöliittymään.
     * @return raportointinäkymä VBoxina.
     */
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
    // Raportointinäkymän luonti
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
}
