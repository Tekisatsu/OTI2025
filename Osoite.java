public class Osoite {
    int id;
    String katuosoite;
    String kaupunki;
    String maa;
    int zip;

    public Osoite() {
        katuosoite = "";
        kaupunki = "";
        maa = "";
        zip = 0;
    }

    public Osoite(int id, String osoite, String kaupunki, String maa, int zip) {
        this.id = id;
        this.katuosoite = osoite;
        this.kaupunki = kaupunki;
        this.maa = maa;
        this.zip = zip;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public String getKaupunki() {
        return kaupunki;
    }

    public void setKaupunki(String kaupunki) {
        this.kaupunki = kaupunki;
    }

    public String getMaa() {
        return maa;
    }

    public void setMaa(String maa) {
        this.maa = maa;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return ""+ katuosoite +", "+kaupunki+", "+zip+", "+maa;
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
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
}
