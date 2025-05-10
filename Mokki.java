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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Mokki {
    int id;
    String nimi;
    Osoite osoite;
    String tila;
    double vuokrahinta;

    public Mokki() {
        this.id = 0;
        this.nimi = "";
        this.osoite = new Osoite();
        this.tila = "";
        this.vuokrahinta = 0;
    }
    public Mokki(int id, String nimi, Osoite osoite, String tila, double vuokrahinta) {
        this.id = id;
        this.nimi = nimi;
        this.osoite = osoite;
        this.tila = tila;
        this.vuokrahinta = vuokrahinta;
    }

    public double getVuokrahinta() {
        return vuokrahinta;
    }

    public void setVuokrahinta(double vuokrahinta) {
        this.vuokrahinta = vuokrahinta;
    }

    public String getTila() {
        return tila;
    }

    public void setTila(String tila) {
        this.tila = tila;
    }

    public Osoite getOsoite() {
        return osoite;
    }

    public void setOsoite(Osoite osoite) {
        this.osoite = osoite;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Mokki mokki = (Mokki) o;
        return Double.compare(vuokrahinta, mokki.vuokrahinta) == 0 && Objects.equals(nimi, mokki.nimi) && Objects.equals(osoite, mokki.osoite) && Objects.equals(tila, mokki.tila);
    }

    @Override
    public String toString() {
        return "Mokki{" +
                "id=" + id +
                ", nimi='" + nimi + '\'' +
                ", osoite=" + osoite +
                ", tila='" + tila + '\'' +
                ", vuokrahinta=" + vuokrahinta +
                '}';
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
    // Mokkinäkymän luonti
    public VBox luoMokkiNakyma() {

        VBox mokkiVbox = new VBox(20);
        mokkiVbox.setStyle("-fx-background-color: lightgray;");
        mokkiVbox.setAlignment(Pos.CENTER_LEFT);
        mokkiVbox.setPadding(new Insets(30));

        Label mokkiOtsikko = new Label("Mokki");
        mokkiOtsikko.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Tekstikenttien luonti
        GridPane mokkiGrid = new GridPane();
        mokkiGrid.setHgap(15);
        mokkiGrid.setVgap(15);
        mokkiGrid.setPadding(new Insets(20));
        mokkiGrid.setAlignment(Pos.TOP_LEFT);

        mokkiGrid.add(new Label("Nimi:"), 0, 1);
        TextField nimiKentta = new TextField();
        mokkiGrid.add(nimiKentta, 1, 1);

        // Osoitteiden näyttäminen
        mokkiGrid.add(new Label("Osoite"), 0, 2);
        ComboBox<Osoite> osoiteComboBox = new ComboBox<>();
        TietokantaYhteysOsoite yhteysOsoite = new TietokantaYhteysOsoite();
        List<Osoite> osoitteet = yhteysOsoite.getAllOsoitteet();
        osoitteet.sort((Comparator.comparing(Osoite::getKatuosoite))); // Järjestetään katuosoitteen mukaan
        osoiteComboBox.setItems(FXCollections.observableArrayList(osoitteet));
        mokkiGrid.add(osoiteComboBox, 1, 2);

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

        // Tilan valitseminen
        mokkiGrid.add(new Label("Tila:"), 0, 3);
        ComboBox<String> tilaComboBox = new ComboBox<>();
        tilaComboBox.getItems().addAll("Saatavissa", "Varattu");
        mokkiGrid.add(tilaComboBox, 1, 3);

        mokkiGrid.add(new Label("Vuokrahinta:"), 0, 4);
        TextField hintaKentta = new TextField();
        mokkiGrid.add(hintaKentta, 1, 4);

        // Painikkeet lisäämiselle, päivittämiselle, ja poistamiselle
        HBox riviButtoneille2 = new HBox(30);
        Button btnLisaa2 = new Button("Lisää");
        Button btnPaivita2 = new Button("Päivitä");
        Button btnPoista2 = new Button("Poista");
        btnPaivita2.setVisible(false);
        btnPoista2.setVisible(false);
        riviButtoneille2.getChildren().addAll(btnLisaa2, btnPaivita2, btnPoista2);
        riviButtoneille2.setAlignment(Pos.CENTER_LEFT);

        // Taulukko aikaisempien mökkien tarkastelulle
        TableView<Mokki> mokkiTable = new TableView<>();
        mokkiTable.setPrefSize(700, 400);
        mokkiTable.setStyle("-fx-border-color: gray;");

        // Taulukon sarakkeeet
        TableColumn<Mokki, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Mokki, String> nimiCol = new TableColumn<>("Nimi");
        nimiCol.setCellValueFactory(new PropertyValueFactory<>("nimi"));
        TableColumn<Mokki, Osoite> osoiteCol = new TableColumn<>("Osoite");
        osoiteCol.setCellValueFactory(new PropertyValueFactory<>("osoite"));
        TableColumn<Mokki, String> tilaCol = new TableColumn<>("Tila");
        tilaCol.setCellValueFactory(new PropertyValueFactory<>("tila"));
        TableColumn<Mokki, Double> hintaCol = new TableColumn<>("Vuokrahinta");
        hintaCol.setCellValueFactory(new PropertyValueFactory<>("vuokrahinta"));

        mokkiTable.getColumns().setAll(idCol, nimiCol, osoiteCol, tilaCol, hintaCol);

        // Täytetään taulukko tietokannan avulla aina kun näkymä alustetaan
        ObservableList<Mokki> mokkiData = FXCollections.observableArrayList(new TietokantaYhteysMokki().readAllMokit());
        mokkiTable.setItems(mokkiData);

        // Korostamalla mökkejä saadaan täytettyä tekstikentät valmiiksi
        mokkiTable.getSelectionModel().selectedItemProperty().addListener((obs, vanha, uusi) -> {
            boolean valittu = uusi != null;
            btnPaivita2.setVisible(valittu);
            btnPoista2.setVisible(valittu);

            if (valittu) {
                nimiKentta.setText(uusi.getNimi());
                osoiteComboBox.setValue(uusi.getOsoite());
                tilaComboBox.setValue(uusi.getTila());
                hintaKentta.setText(String.valueOf(uusi.getVuokrahinta()));
            }
        });

        // Klikkaus taulukon ulkopuolella tyhjentää valinnan ja kentät
        mokkiVbox.setOnMouseClicked(event -> {

            // Jos klikattu ei ollut taulukko tai mikään sen lapsi
            if (!mokkiTable.equals(event.getTarget()) && !mokkiTable.isHover()) {
                // Tyhjennetään valinta
                mokkiTable.getSelectionModel().clearSelection();

                // Tyhjennetään kentät
                nimiKentta.clear();
                osoiteComboBox.setValue(null);
                tilaComboBox.setValue(null);
                hintaKentta.clear();
            }
        });

        // Mökkien lisääminen taulukkoon
        btnLisaa2.setOnAction(e -> {
            try {
                TietokantaYhteysMokki yhteysMokki = new TietokantaYhteysMokki();

                String nimi = nimiKentta.getText();
                Osoite osoite = osoiteComboBox.getValue();
                String tila = tilaComboBox.getValue();
                double vuokrahinta = Double.parseDouble(hintaKentta.getText());
                Mokki mokki = new Mokki(0, nimi, osoite, tila, vuokrahinta);

                yhteysMokki.createMokki(mokki);

                mokkiTable.setItems(FXCollections.observableArrayList(yhteysMokki.readAllMokit()));

            } catch (Exception ex) {
                System.err.println("Virhe lisättäessä: " + ex.getMessage());
            }
        });

        // Mökin päivittäminen
        btnPaivita2.setOnAction(e -> {
            Mokki valittu = mokkiTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                try {
                    valittu.setNimi(nimiKentta.getText());
                    valittu.setOsoite(osoiteComboBox.getValue());
                    valittu.setTila(tilaComboBox.getValue());
                    valittu.setVuokrahinta(Double.parseDouble(hintaKentta.getText()));

                    TietokantaYhteysMokki yhteysMokki = new TietokantaYhteysMokki();
                    yhteysMokki.updateMokki(valittu);

                    mokkiTable.setItems(FXCollections.observableArrayList(yhteysMokki.readAllMokit()));
                } catch (Exception ex) {
                    System.err.println("Virhe päivityksessä: " + ex.getMessage());
                }
            }
        });

        // Mökin poistaminen taulukosta
        btnPoista2.setOnAction(e -> {
            Mokki valittu = mokkiTable.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                TietokantaYhteysMokki yhteysMokki = new TietokantaYhteysMokki();
                yhteysMokki.deleteMokki(valittu.getId());

                mokkiTable.setItems(FXCollections.observableArrayList(yhteysMokki.readAllMokit()));

                // Tyhjennetään kentät
                nimiKentta.clear();
                osoiteComboBox.setValue(null);
                tilaComboBox.setValue(null);
                hintaKentta.clear();
            }
        });

        mokkiVbox.getChildren().addAll(mokkiOtsikko, mokkiGrid, riviButtoneille2, mokkiTable);
        return mokkiVbox;
    }
}
