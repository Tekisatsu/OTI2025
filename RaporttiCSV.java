import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * RaporttiCSV-luokka sisältää metodit raportin csv-tiedoston luontiin.
 */
public class RaporttiCSV {

    /**
     * Lukee raportin tiedot csv-tiedostosta.
     * @param tiedostoNimi String
     * @return Luetut raportit listana.
     */
    public static List<Raportti> lueRaportitCSV(String tiedostoNimi) {
        List<Raportti> raportit = new ArrayList<>();

        File tiedosto = new File(tiedostoNimi);
        if (!tiedosto.exists()) {
            System.out.println("Aikaisempaa tiedostoa " + tiedostoNimi + " ei löytynyt");
            return raportit; // palauttaa tyhjän listan
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(tiedostoNimi))) {
            String rivi = reader.readLine(); // ohita otsikkorivi

            while ((rivi = reader.readLine()) != null) {
                String[] osat = rivi.split(",");

                Raportti r = new Raportti(
                        LocalDate.parse(osat[0]),
                        LocalDate.parse(osat[1]),
                        Integer.parseInt(osat[2]),
                        new BigDecimal(osat[3]),
                        osat[5],
                        Integer.parseInt(osat[6]),
                        Integer.parseInt(osat[7])
                );

                raportit.add(r);
            }

        } catch (IOException e) {
            System.err.println("Virhe CSV-tiedoston lukemisessa: " + e.getMessage());
        }

        return raportit;
    }

    /**
     * Metodi luotujen raporttien kirjoittamiseen csv-tiedostoon.
     * @param raportit List
     * @param tiedostoPolku String
     */
    public static void kirjoitaRaportitCSV(List<Raportti> raportit, String tiedostoPolku) {
        try (FileWriter writer = new FileWriter(tiedostoPolku)) {
            writer.write("Alku,Loppu,Varauksia,Laskutus,SuosituinMokki,Keskikesto,Asiakkaita\n");

            for (Raportti r : raportit) {
                writer.write(String.format("%s,%s,%d,%.2f,%s,%d,%d\n",
                        r.getAlkuPvm(),
                        r.getLoppuPvm(),
                        r.getVaraustenMaara(),
                        r.getKokonaislaskutus(),
                        r.getSuosituinMokki(),
                        r.getKeskimKesto(),
                        r.getAsiakasmaara()));
            }

            System.out.println("Tulos tallennettu tiedostoon: " + tiedostoPolku);
        } catch (IOException e) {
            System.err.println("Virhe CSV-tiedoston kirjoittamisessa: " + e.getMessage());
        }
    }
}
