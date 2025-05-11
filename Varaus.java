import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**Luokka varausten tiedoille ja niiden käsittelyyn**/
public class Varaus {
    private int id;
    private Asiakas asiakas;
    private Mokki mokki;
    private Lasku lasku;
    private int henkilomaara;
    private LocalDate aloituspaivamaara;
    private LocalDate paattymispaivamaara;

    public Varaus() {
        this.id = 0;
        this.asiakas = new Asiakas();
        this.mokki = new Mokki();
        this.lasku = new Lasku();
        this.henkilomaara = 0;
        this.aloituspaivamaara = null;
        this.paattymispaivamaara = null;
    }
    public Varaus(int id, Asiakas asiakas, Mokki mokki, Lasku lasku, int henkilomaara,LocalDate alkamispaivamaara, LocalDate paattymispaivamaara) {
        this.id = id;
        this.asiakas = asiakas;
        this.mokki = mokki;
        this.lasku = lasku;
        this.henkilomaara = henkilomaara;
        this.aloituspaivamaara = alkamispaivamaara;
        this.paattymispaivamaara = paattymispaivamaara;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Asiakas getAsiakas() {
        return asiakas;
    }

    public void setAsiakas(Asiakas asiakas) {
        this.asiakas = asiakas;
    }

    public Mokki getMokki() {
        return mokki;
    }

    public void setMokki(Mokki mokki) {
        this.mokki = mokki;
    }

    public Lasku getLasku() {
        return lasku;
    }

    public void setLasku(Lasku lasku) {
        this.lasku = lasku;
    }

    public int getHenkilomaara() {
        return henkilomaara;
    }

    public void setHenkilomaara(int henkilomaara) {
        this.henkilomaara = henkilomaara;
    }

    public LocalDate getAloituspaivamaara() {
        return aloituspaivamaara;
    }

    public void setAloituspaivamaara(LocalDate aloituspaivamaara) {
        this.aloituspaivamaara = aloituspaivamaara;
    }

    public LocalDate getPaattymispaivamaara() {
        return paattymispaivamaara;
    }

    public void setPaattymispaivamaara(LocalDate paattymispaivamaara) {
        this.paattymispaivamaara = paattymispaivamaara;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Varaus varaus = (Varaus) o;
        return Objects.equals(aloituspaivamaara, varaus.aloituspaivamaara) && Objects.equals(paattymispaivamaara, varaus.paattymispaivamaara) && Objects.equals(this.mokki,((Varaus) o).mokki);
    }


    @Override
    public String toString() {
        return "Varaus{" +
                "id=" + id +
                ", asiakas=" + asiakas +
                ", mokki=" + mokki +
                ", lasku=" + lasku +
                ", alkamispaivamaara=" + aloituspaivamaara +
                ", paattymispaivamaara=" + paattymispaivamaara +
                ", henkilomaara=" + henkilomaara +
                '}';
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
    // Varausnakyman luonti
    /**Luo käyttöliittymää varten Vbox varaus näkymästä
     * @return VBox varausnäkymästä**/
    public VBox luoVarausNakyma() {

        VBox varausVbox = new VBox(20);
        varausVbox.setStyle("-fx-background-color: lightgray;");
        varausVbox.setAlignment(Pos.CENTER_LEFT);
        varausVbox.setPadding(new Insets(30));

        Label varausOtsikko = new Label("Varaus");
        varausOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Tekstikenttien luonti
        GridPane varausGrid = new GridPane();
        varausGrid.setHgap(15);
        varausGrid.setVgap(15);
        varausGrid.setPadding(new Insets(20));
        varausGrid.setAlignment(Pos.TOP_LEFT);

        // Asiakkaan näyttäminen
        varausGrid.add(new Label("Asiakas"), 0, 1);
        ComboBox<Asiakas> asiakasComboBox = new ComboBox<>();
        TietokantaYhteysAsiakas yhteysAsiakas = new TietokantaYhteysAsiakas();
        List<Asiakas> asiakkaat = yhteysAsiakas.getAllAsiakkaat();
        asiakkaat.sort((Comparator.comparing(Asiakas::getNimi))); // Järjestetään nimen mukaan
        asiakasComboBox.setItems(FXCollections.observableArrayList(asiakkaat));
        varausGrid.add(asiakasComboBox, 1, 1);

        // Miltä valikko näyttää sisältäpäin
        asiakasComboBox.setCellFactory(cb -> new ListCell<>() {
            /**päivittää ComboBox näkymää asiakkaan tiedoilla
             * @param item päivitettävä asiakas
             * @param empty onko kenttä tyhjä**/
            @Override
            protected void updateItem(Asiakas item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNimi() + ", " + item.getPuhelinnumero() + ", " + item.getSahkoposti() + ", " + item.getOsoite().getKatuosoite());
            }
        });
        // Miltä valikko näyttää ulkoapäin
        asiakasComboBox.setButtonCell(new ListCell<>() {
            /**päivittää ComboBox näkymää asiakkaan tiedoilla
             * @param item päivitettävä asiakas
             * @param empty onko kenttä tyhjä**/
            @Override
            protected void updateItem(Asiakas item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNimi() + ", " + item.getPuhelinnumero() + ", " + item.getSahkoposti() + ", " + item.getOsoite().getKatuosoite());
            }
        });

        // Mökin näyttäminen
        varausGrid.add(new Label("Mökki"), 0, 2);
        ComboBox<Mokki> mokkiComboBox = new ComboBox<>();
        TietokantaYhteysMokki yhteysMokki = new TietokantaYhteysMokki();
        List<Mokki> mokit = yhteysMokki.readAllMokit();
        mokit.sort((Comparator.comparing(Mokki::getNimi))); // Järjestetään nimen mukaan
        mokkiComboBox.setItems(FXCollections.observableArrayList(mokit));
        varausGrid.add(mokkiComboBox, 1, 2);

        // Miltä valikko näyttää sisältäpäin
        mokkiComboBox.setCellFactory(cb -> new ListCell<>() {
            /**päivittää ComboBox näkymää mökin tiedoilla
             * @param item päivitettävä mökki
             * @param empty onko kenttä tyhjä**/
            @Override
            protected void updateItem(Mokki item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNimi() + ", " + item.getTila() + ", " + item.getVuokrahinta() + ", " + item.getOsoite().getKatuosoite());
            }
        });
        // Miltä valikko näyttää ulkoapäin
        mokkiComboBox.setButtonCell(new ListCell<>() {
            /**päivittää ComboBox näkymää mökin tiedoilla
             * @param item päivitettävä mökki
             * @param empty onko kenttä tyhjä**/
            @Override
            protected void updateItem(Mokki item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNimi() + ", " + item.getTila() + ", " + item.getVuokrahinta() + ", " + item.getOsoite().getKatuosoite());
            }
        });

        // Laskun näyttäminen
        varausGrid.add(new Label("Lasku"), 0, 3);
        ComboBox<Lasku> laskuComboBox = new ComboBox<>();
        TietokantaYhteysLasku yhteysLasku = new TietokantaYhteysLasku();
        List<Lasku> laskut = yhteysLasku.getAllLaskut();
        laskut.sort((Comparator.comparing(Lasku::getViitenumero))); // Järjestetään viitenumero mukaan
        laskuComboBox.setItems(FXCollections.observableArrayList(laskut));
        varausGrid.add(laskuComboBox, 1, 3);

        // Miltä valikko näyttää sisältäpäin
        laskuComboBox.setCellFactory(cb -> new ListCell<>() {
            /**päivittää ComboBox näkymää laskujen tiedoilla
             * @param item päivitettävä lasku
             * @param empty onko kenttä tyhjä**/
            @Override
            protected void updateItem(Lasku item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getViitenumero() + ", " + item.getErapaiva() + ", " + item.getMaksaja() + ", " + item.getMaara());
            }
        });
        // Miltä valikko näyttää ulkoapäin
        laskuComboBox.setButtonCell(new ListCell<>() {
            /**päivittää ComboBox näkymää laskujen tiedoilla
             * @param item päivitettävä lasku
             * @param empty onko kenttä tyhjä**/
            @Override
            protected void updateItem(Lasku item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getViitenumero() + ", " + item.getErapaiva() + ", " + item.getMaksaja() + ", " + item.getMaara());
            }
        });

        varausGrid.add(new Label("Henkilömäärä:"), 0, 4);
        TextField henkiloKentta = new TextField();
        varausGrid.add(henkiloKentta, 1, 4);

        varausGrid.add(new Label("Aloituspäivämäärä:"), 0, 5);
        DatePicker aloitusKentta = new DatePicker();
        varausGrid.add(aloitusKentta, 1, 5);

        varausGrid.add(new Label("Päättymispäivämäärä:"), 0, 6);
        DatePicker paattymisKentta = new DatePicker();
        varausGrid.add(paattymisKentta, 1, 6);

        // Painikkeet lisäämiselle, päivittämiselle, ja poistamiselle
        HBox riviButtoneille1 = new HBox(30);
        Button btnLisaa1 = new Button("Lisää");
        Button btnPaivita1 = new Button("Päivitä");
        Button btnPoista1 = new Button("Poista");
        btnPaivita1.setVisible(false);
        btnPoista1.setVisible(false);
        riviButtoneille1.getChildren().addAll(btnLisaa1, btnPaivita1, btnPoista1);
        riviButtoneille1.setAlignment(Pos.CENTER_LEFT);

        // Taulukko aikaisempien varauksien tarkastelulle
        TableView<Varaus> varausTable = new TableView<>();
        varausTable.setPrefSize(700, 400);
        varausTable.setStyle("-fx-border-color: gray;");

        // Taulukon sarakkeeet
        TableColumn<Varaus, Integer> varausIdCol = new TableColumn<>("ID");
        varausIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Varaus, Asiakas> asiakasCol = new TableColumn<>("Asiakas");
        asiakasCol.setCellValueFactory(new PropertyValueFactory<>("asiakas"));
        TableColumn<Varaus, Mokki> mokkiCol = new TableColumn<>("Mökki");
        mokkiCol.setCellValueFactory(new PropertyValueFactory<>("mokki"));
        TableColumn<Varaus, Lasku> laskuCol = new TableColumn<>("Lasku");
        laskuCol.setCellValueFactory(new PropertyValueFactory<>("lasku"));
        TableColumn<Varaus, Integer> henkiloCol = new TableColumn<>("Henkilömäärä");
        henkiloCol.setCellValueFactory(new PropertyValueFactory<>("henkilomaara"));
        TableColumn<Varaus, LocalDate> aloitusCol = new TableColumn<>("Aloituspäivämäärä");
        aloitusCol.setCellValueFactory(new PropertyValueFactory<>("aloituspaivamaara"));
        TableColumn<Varaus, LocalDate> paattymisCol = new TableColumn<>("Päättymispäivämäärä");
        paattymisCol.setCellValueFactory(new PropertyValueFactory<>("paattymispaivamaara"));

        varausTable.getColumns().setAll(varausIdCol, asiakasCol, mokkiCol, laskuCol, henkiloCol, aloitusCol, paattymisCol);

        // Täytetään taulukko tietokannan avulla aina kun näkymä alustetaan
        ObservableList<Varaus> varausData = FXCollections.observableArrayList(new TietokantaYhteysVaraus().getAllVaraukset());
        varausTable.setItems(varausData);

        // Korostamalla asiakkaita saadaan täytettyä tekstikentät valmiiksi
        varausTable.getSelectionModel().selectedItemProperty().addListener((obs, vanha, uusi) -> {
            boolean valittu = uusi != null;
            btnPaivita1.setVisible(valittu);
            btnPoista1.setVisible(valittu);

            if (valittu) {
                asiakasComboBox.setValue(uusi.getAsiakas());
                mokkiComboBox.setValue(uusi.getMokki());
                laskuComboBox.setValue(uusi.getLasku());
                henkiloKentta.setText(String.valueOf(uusi.getHenkilomaara()));
                aloitusKentta.setValue(uusi.getAloituspaivamaara());
                paattymisKentta.setValue(uusi.getPaattymispaivamaara());
            }
        });

        // Klikkaus taulukon ulkopuolella tyhjentää valinnan ja kentät
        varausVbox.setOnMouseClicked(event -> {

            // Jos klikattu ei ollut taulukko tai mikään sen lapsi
            if (!varausTable.equals(event.getTarget()) && !varausTable.isHover()) {
                // Tyhjennetään valinta
                varausTable.getSelectionModel().clearSelection();

                // Tyhjennetään kentät
                asiakasComboBox.setValue(null);
                mokkiComboBox.setValue(null);
                laskuComboBox.setValue(null);
                henkiloKentta.clear();
                aloitusKentta.setValue(null);
                paattymisKentta.setValue(null);
            }
        });

        // Varauksien lisääminen taulukkoon
        btnLisaa1.setOnAction(e -> {
            try {
                TietokantaYhteysVaraus yhteysVaraus = new TietokantaYhteysVaraus();

                Asiakas asiakas = asiakasComboBox.getValue();
                Mokki mokki = mokkiComboBox.getValue();
                Lasku lasku = laskuComboBox.getValue();
                int henkilomaara = Integer.parseInt(henkiloKentta.getText());
                LocalDate aloituspaivamaara = aloitusKentta.getValue();
                LocalDate paattymispaivamaara = paattymisKentta.getValue();

                Varaus varaus = new Varaus(0, asiakas, mokki, lasku, henkilomaara, aloituspaivamaara, paattymispaivamaara);

                yhteysVaraus.createVaraus(varaus);

                varausTable.setItems(FXCollections.observableArrayList(yhteysVaraus.getAllVaraukset()));

            } catch (Exception ex) {
                System.err.println("Virhe lisättäessä: " + ex.getMessage());
            }
        });

        // Varauksen päivittäminen
        btnPaivita1.setOnAction(e -> {
            Varaus valittu = varausTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                try {
                    valittu.setAsiakas(asiakasComboBox.getValue());
                    valittu.setMokki(mokkiComboBox.getValue());
                    valittu.setLasku(laskuComboBox.getValue());
                    valittu.setHenkilomaara(Integer.parseInt(henkiloKentta.getText()));
                    valittu.setAloituspaivamaara(aloitusKentta.getValue());
                    valittu.setPaattymispaivamaara(paattymisKentta.getValue());

                    TietokantaYhteysVaraus yhteysVaraus = new TietokantaYhteysVaraus();
                    yhteysVaraus.updateVaraus(valittu);

                    varausTable.setItems(FXCollections.observableArrayList(yhteysVaraus.getAllVaraukset()));
                } catch (Exception ex) {
                    System.err.println("Virhe päivityksessä: " + ex.getMessage());
                }
            }
        });

        // Asiakkaan poistaminen taulukosta
        btnPoista1.setOnAction(e -> {
            Varaus valittu = varausTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                TietokantaYhteysVaraus yhteysVaraus = new TietokantaYhteysVaraus();
                yhteysVaraus.deleteVaraus(valittu.getId());

                varausTable.setItems(FXCollections.observableArrayList(yhteysVaraus.getAllVaraukset()));

                // Tyhjennetään kentät
                asiakasComboBox.setValue(null);
                mokkiComboBox.setValue(null);
                laskuComboBox.setValue(null);
                henkiloKentta.clear();
                aloitusKentta.setValue(null);
                paattymisKentta.setValue(null);
            }
        });

        varausVbox.getChildren().addAll(varausOtsikko, varausGrid, riviButtoneille1, varausTable);
        return varausVbox;
    }
}
