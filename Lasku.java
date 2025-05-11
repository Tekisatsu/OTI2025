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
import java.util.Objects;

public class Lasku {

    private int id;
    private String viitenumero;
    private LocalDate erapaiva;
    private String maksaja;
    private String saaja;
    private String ytunnus;
    private double alvprosentti;
    private double maara;

    public Lasku() {
    }

    public Lasku(int id, String viitenumero, LocalDate erapaiva, String maksaja, String saaja, String ytunnus, double alvprosentti, double maara) {
        this.id = id;
        this.viitenumero = viitenumero;
        this.erapaiva = erapaiva;
        this.maksaja = maksaja;
        this.saaja = saaja;
        this.ytunnus = ytunnus;
        this.alvprosentti = alvprosentti;
        this.maara = maara;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getViitenumero() {
        return viitenumero;
    }

    public void setViitenumero(String viitenumero) {
        this.viitenumero = viitenumero;
    }

    public LocalDate getErapaiva() {
        return erapaiva;
    }

    public void setErapaiva(LocalDate erapaiva) {
        this.erapaiva = erapaiva;
    }

    public String getMaksaja() {
        return maksaja;
    }

    public void setMaksaja(String maksaja) {
        this.maksaja = maksaja;
    }

    public String getSaaja() {
        return saaja;
    }

    public void setSaaja(String saaja) {
        this.saaja = saaja;
    }

    public String getYtunnus() {
        return ytunnus;
    }

    public void setYtunnus(String ytunnus) {
        this.ytunnus = ytunnus;
    }

    public double getAlvprosentti() {
        return alvprosentti;
    }

    public void setAlvprosentti(double alvprosentti) {
        this.alvprosentti = alvprosentti;
    }

    public double getMaara() {
        return maara;
    }

    public void setMaara(double maara) {
        this.maara = maara;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Lasku lasku = (Lasku) o;
        return Double.compare(alvprosentti, lasku.alvprosentti) == 0 && Double.compare(maara, lasku.maara) == 0 && Objects.equals(viitenumero, lasku.viitenumero) && Objects.equals(erapaiva, lasku.erapaiva) && Objects.equals(maksaja, lasku.maksaja) && Objects.equals(saaja, lasku.saaja) && Objects.equals(ytunnus, lasku.ytunnus);
    }

    @Override
    public String toString() {
        return ""+ maara +", "+erapaiva+", "+viitenumero;
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
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
}
