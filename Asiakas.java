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

public class Asiakas {

    private int id;
    private String nimi;
    private String sahkoposti;
    private String puhelinnumero;
    private Osoite osoite;

    public Asiakas() {
        this.id = 0;
        this.nimi = "";
        this.sahkoposti = "";
        this.puhelinnumero = "";
        this.osoite = new Osoite();
    }

    public Asiakas(int id, String nimi, String sahkoposti, String puhelinnumero, Osoite osoite) {
        this.id = id;
        this.nimi = nimi;
        this.sahkoposti = sahkoposti;
        this.puhelinnumero = puhelinnumero;
        this.osoite = osoite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    public String getPuhelinnumero() {
        return puhelinnumero;
    }

    public void setPuhelinnumero(String puhelinnumero) {
        this.puhelinnumero = puhelinnumero;
    }

    public Osoite getOsoite() {
        return osoite;
    }

    public void setOsoite(Osoite osoite) {
        this.osoite = osoite;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Asiakas asiakas = (Asiakas) o;
        return Objects.equals(nimi, asiakas.nimi) && Objects.equals(sahkoposti, asiakas.sahkoposti) && Objects.equals(puhelinnumero, asiakas.puhelinnumero) && Objects.equals(osoite, asiakas.osoite);
    }

    @Override
    public String toString() {
        return nimi+", "+sahkoposti+", "+puhelinnumero+", "+osoite;
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
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
}
