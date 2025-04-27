import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class kayttoliittymaKirjautuminenPohja extends Application {
    private List<Pelaaja> pelaajat;
    private List<String> pelaajaNimet;
    private List<Integer> pelaajaPisteet;
    private Stage primaryStage;
    private int vaikeusTaso;

    @Override
    public void start(Stage alkuIkkuna) {
        this.primaryStage = alkuIkkuna;
        lueEnnatykset();
        nimiKysely();
    }

    private void nimiKysely() {
        VBox nimet = new VBox(15);
        nimet.setAlignment(Pos.CENTER);
        nimet.setPadding(new Insets(20));

        Label ohjeNimille = new Label("Syötä 1-3 pelaajan nimet:");
        ohjeNimille.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        HBox pelaaja1Boksi = new HBox(10);
        pelaaja1Boksi.setAlignment(Pos.CENTER);
        TextField nimiKentta1 = new TextField();
        nimiKentta1.setPromptText("Pelaaja 1");
        nimiKentta1.setPrefWidth(200);
        Button lisaaPelaaja1Painike = new Button("Lisää pelaaja");
        Text tulos1Teksti = new Text();
        pelaaja1Boksi.getChildren().addAll(nimiKentta1, lisaaPelaaja1Painike);

        HBox pelaaja2Boksi = new HBox(10);
        pelaaja2Boksi.setAlignment(Pos.CENTER);
        TextField nimiKentta2 = new TextField();
        nimiKentta2.setPromptText("Pelaaja 2 (valinnainen)");
        nimiKentta2.setPrefWidth(200);
        Button lisaaPelaaja2Painike = new Button("Lisää pelaaja");
        Text tulos2Teksti = new Text();
        pelaaja2Boksi.getChildren().addAll(nimiKentta2, lisaaPelaaja2Painike);

        HBox pelaaja3Boksi = new HBox(10);
        pelaaja3Boksi.setAlignment(Pos.CENTER);
        TextField nimiKentta3 = new TextField();
        nimiKentta3.setPromptText("Pelaaja 3 (valinnainen)");
        nimiKentta3.setPrefWidth(200);
        Button lisaaPelaaja3Painike = new Button("Lisää pelaaja");
        Text tulos3Teksti = new Text();
        pelaaja3Boksi.getChildren().addAll(nimiKentta3, lisaaPelaaja3Painike);

        HBox vaikeusTasoBox = new HBox(10);
        vaikeusTasoBox.setAlignment(Pos.CENTER);
        Label vaikeusTasoLabel = new Label("Vaikeustaso:");
        ComboBox<Integer> vaikeusTasoValinta = new ComboBox<>();
        vaikeusTasoValinta.getItems().addAll(1, 2, 3);
        vaikeusTasoValinta.setValue(1);
        vaikeusTasoBox.getChildren().addAll(vaikeusTasoLabel, vaikeusTasoValinta);

        pelaajat = new ArrayList<>();

        lisaaPelaaja1Painike.setOnAction(e -> {
            String nimi = nimiKentta1.getText().trim();
            if (!nimi.isEmpty() && !onkoPelaaja(nimi)) {
                pelaajat.add(new Pelaaja(nimi));
                int indeksi = pelaajaNimet.indexOf(nimi);
                int ennatys;
                if (indeksi != -1) {
                    ennatys = pelaajaPisteet.get(indeksi);
                } else {
                    ennatys = 0;
                }
                tulos1Teksti.setText(nimi + " - Ennätystulos: €" + ennatys);
                nimiKentta1.setDisable(true);
                lisaaPelaaja1Painike.setDisable(true);
            }
        });

        lisaaPelaaja2Painike.setOnAction(e -> {
            String nimi = nimiKentta2.getText().trim();
            if (!nimi.isEmpty() && !onkoPelaaja(nimi)) {
                pelaajat.add(new Pelaaja(nimi));
                int indeksi = pelaajaNimet.indexOf(nimi);
                int ennatys;
                if (indeksi != -1) {
                    ennatys = pelaajaPisteet.get(indeksi);
                } else {
                    ennatys = 0;
                }
                tulos2Teksti.setText(nimi + " - Ennätystulos: €" + ennatys);
                nimiKentta2.setDisable(true);
                lisaaPelaaja2Painike.setDisable(true);
            }
        });

        lisaaPelaaja3Painike.setOnAction(e -> {
            String nimi = nimiKentta3.getText().trim();
            if (!nimi.isEmpty() && !onkoPelaaja(nimi)) {
                pelaajat.add(new Pelaaja(nimi));
                int indeksi = pelaajaNimet.indexOf(nimi);
                int ennatys;
                if (indeksi != -1) {
                    ennatys = pelaajaPisteet.get(indeksi);
                } else {
                    ennatys = 0;
                }
                tulos3Teksti.setText(nimi + " - Ennätystulos: €" + ennatys);
                nimiKentta3.setDisable(true);
                lisaaPelaaja3Painike.setDisable(true);
            }
        });

        Button aloitusNappi = new Button("Aloita peli");
        aloitusNappi.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        aloitusNappi.setPrefWidth(150);
        Text virheViesti = new Text();
        virheViesti.setFill(Color.RED);

        aloitusNappi.setOnAction(e -> {
            if (pelaajat.isEmpty()) {
                virheViesti.setText("Lisää ainakin yksi pelaaja!");
            } else {
                vaikeusTaso = vaikeusTasoValinta.getValue();
                // alustaPeli();
                // naytaPeliNakyma();
            }
        });

        nimet.getChildren().addAll(
                ohjeNimille, pelaaja1Boksi, tulos1Teksti, pelaaja2Boksi, tulos2Teksti,
                pelaaja3Boksi, tulos3Teksti, vaikeusTasoBox, virheViesti, aloitusNappi);
        Scene nimiScene = new Scene(nimet, 500, 500);
        primaryStage.setScene(nimiScene);
        primaryStage.setTitle("Onnenpyörä - pelaajien nimet");
        primaryStage.show();
    }

    private void lueEnnatykset() {
        pelaajaNimet = new ArrayList<>();
        pelaajaPisteet = new ArrayList<>();
        try {
            File tiedosto = new File("pelaajat.txt");
            if (!tiedosto.exists()) {
                tiedosto.createNewFile();
                return;
            }
            Scanner lukija = new Scanner(tiedosto);
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                String[] osat = rivi.split(",");
                if (osat.length == 2) {
                    pelaajaNimet.add(osat[0].trim());
                    pelaajaPisteet.add(Integer.parseInt(osat[1].trim()));
                }
            }
            lukija.close();
        } catch (IOException e) {
            System.out.println("Virheellinen tiedoston käsittely - " + e.getMessage());
        }
    }

    private void tallennaEnnatys(String nimi, int pisteet) {
        int indeksi = pelaajaNimet.indexOf(nimi);
        if (indeksi == -1) {
            pelaajaNimet.add(nimi);
            pelaajaPisteet.add(pisteet);
        } else if (pelaajaPisteet.get(indeksi) < pisteet) {
            pelaajaPisteet.set(indeksi, pisteet);
        }

        try {
            FileWriter kirjoittaja = new FileWriter("pelaajat.txt");
            for (int i = 0; i < pelaajaNimet.size(); i++) {
                kirjoittaja.write(pelaajaNimet.get(i) + ", " + pelaajaPisteet.get(i) + "\n");
            }
            kirjoittaja.close();
        } catch (IOException e) {
            System.out.println("Tapahtui virhe tiedoston tallentamisessa - " + e.getMessage());
        }
    }

    private boolean onkoPelaaja(String nimi) {
        for (Pelaaja pelaaja : pelaajat) {
            if (pelaaja.getNimi().equalsIgnoreCase(nimi)) {
                return true;
            }
        }
        return false;
    }

    class Pelaaja {
        private String nimi;
        private int pisteet;
        private boolean onkoVuorossa;

        public Pelaaja(String nimi) {
            this.nimi = nimi;
            this.pisteet = 0;
            this.onkoVuorossa = false;
        }

        public String getNimi() {
            return nimi;
        }

        public int getPisteet() {
            return pisteet;
        }

        public void lisaaPisteita(int maara) {
            this.pisteet += maara;
        }

        public boolean isOnkoVuorossa() {
            return onkoVuorossa;
        }

        public void setOnkoVuorossa(boolean onkoVuorossa) {
            this.onkoVuorossa = onkoVuorossa;
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}