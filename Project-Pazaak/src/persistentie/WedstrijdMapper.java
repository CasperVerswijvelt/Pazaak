/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Kaart;
import domein.Wedstrijd;
import exceptions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casper
 */
public class WedstrijdMapper {

    private final SpelerMapper sm;
    private final KaartMapper km;

    public WedstrijdMapper() {
        this.sm = new SpelerMapper();
        this.km = new KaartMapper();
    }

    public void slaWedstrijdOp(Wedstrijd wedstrijd, String wedstrijdNaam) {

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            if (bestaatWedstrijd(wedstrijdNaam)) {
                throw new GameAlreadyExistsException("ERROR SAVING GAME");
            }

            //Wedstrijd
            PreparedStatement query = conn.prepareStatement("INSERT INTO " + Connectie.DBNAAM + ".Wedstrijd (naam, speler1AanBeurt, speler1, speler2, score1,score2)"
                    + "VALUES (?, ?, ?, ?, ?,?)");
            query.setString(1, wedstrijdNaam);
            query.setInt(2, wedstrijd.geefSpelerAanBeurt().equals(wedstrijd.geefSpelers().get(0).getNaam())?1:0);
            query.setString(3, wedstrijd.geefSpelers().get(0).getNaam());
            query.setString(4, wedstrijd.geefSpelers().get(1).getNaam());
            query.setInt(5, wedstrijd.geefTussenstand()[0]);
            query.setInt(6, wedstrijd.geefTussenstand()[1]);
            query.executeUpdate();

            //Wedstrijdkaarten
            for (int i = 0; i < 2; i++) {
                List<Kaart> wedstrijdStapel = wedstrijd.geefWedstrijdStapel(i);
                for (int j = 0; j < wedstrijdStapel.size(); j++) {
                    query = conn.prepareStatement("INSERT INTO " + Connectie.DBNAAM + ".WedstrijdKaart (KaartTypeId, SpelerNaam, WedstrijdNaam)"
                            + "VALUES (?, ?, ?)");
                    query.setInt(1, km.geefKaartId(wedstrijdStapel.get(j)));
                    query.setString(2, wedstrijd.geefSpelers().get(i).getNaam());
                    query.setString(3, wedstrijdNaam);
                    query.executeUpdate();
                }

            }

            query.close();
        } catch (SQLException ex) {
            throw new GameSaveDatabaseException(ex);
        }
    }

    public boolean bestaatWedstrijd(String wedstrijdNaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            //Wedstrijd
            PreparedStatement query = conn.prepareStatement("SELECT naam FROM " + Connectie.DBNAAM + ".Wedstrijd WHERE naam = ?");
            query.setString(1, wedstrijdNaam);
            ResultSet rs = query.executeQuery();

            boolean bestaat = rs.next();
            query.close();
            return bestaat;

        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public Wedstrijd laadWedstrijd(String wedstrijdNaam) {
        try {
            if (!bestaatWedstrijd(wedstrijdNaam)) {
                throw new GameDoesntExistException("ERROR LOADING GAME");
            }

            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);

            String speler1 = "", speler2 = "";
            boolean speler1Begint =true;
            int score1 = 0, score2 = 0;
            List<Kaart> ws1 = new ArrayList<>(), ws2 = new ArrayList<>();

            //Eerste query (scores en spelernamen)
            PreparedStatement query = conn.prepareStatement("SELECT * FROM " + Connectie.DBNAAM + ".Wedstrijd WHERE naam = ?");
            query.setString(1, wedstrijdNaam);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                speler1Begint = rs.getInt("speler1AanBeurt")!=0;
                speler1 = rs.getString("speler1");
                speler2 = rs.getString("speler2");
                score1 = rs.getInt("score1");
                score2 = rs.getInt("score2");
            }

            //Tweede query (kaartenlijst en spelernamen)
            query = conn.prepareStatement("SELECT waarde, type, prijs,spelerNaam FROM " + Connectie.DBNAAM + ".WedstrijdKaart LEFT JOIN " + Connectie.DBNAAM + ".KaartType ON " + Connectie.DBNAAM + ".WedstrijdKaart.KaartTypeId = " + Connectie.DBNAAM + ".KaartType.id WHERE WedstrijdNaam = ? ");
            query.setString(1, wedstrijdNaam);

            rs = query.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                String spelerNaam = rs.getString("spelerNaam");
                int waarde = rs.getInt("waarde");
                int prijs = rs.getInt("prijs");

                Kaart kaart = new Kaart(waarde, type.charAt(0), prijs);

                if (spelerNaam.equals(speler1)) {
                    ws1.add(kaart);
                } else {
                    ws2.add(kaart);
                }
            }

            //Derde query (wedstrijd zelf verwijderen)
            query = conn.prepareStatement("DELETE FROM " + Connectie.DBNAAM + ".Wedstrijd WHERE naam = ?");
            query.setString(1, wedstrijdNaam);
            query.executeUpdate();

            query.close();

            //Wedstrijd aanmaken met gegeven gegevens
            return new Wedstrijd(sm.geefSpeler(speler1), sm.geefSpeler(speler2), ws1, ws2, speler1Begint, score1, score2);

        } catch (SQLException ex) {
            throw new GameLoadDatabaseException(ex);
        }

    }

    public String[][] geefWedstrijdenOverzicht() {
        List<String[]> lijst = new ArrayList<>();
        String[][] res;
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);

            PreparedStatement query = conn.prepareStatement("SELECT naam, speler1, speler2, score1, score2 FROM " + Connectie.DBNAAM + ".Wedstrijd");
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                String[] info = new String[5];
                info[0] = rs.getString("naam");
                info[1] = rs.getString("speler1");
                info[2] = rs.getInt("score1") + "";
                info[3] = rs.getString("speler2");
                info[4] = rs.getInt("score2") + "";
                lijst.add(info);
            }

            res = new String[lijst.size()][];

            for (int i = 0; i < lijst.size(); i++) {
                res[i] = lijst.get(i);
            }

            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return res;
    }

    public void verwijderWedstrijd(String wedstrijd) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("DELETE FROM " + Connectie.DBNAAM + ".Wedstrijd WHERE NAAM = ?");
            query.setString(1, wedstrijd);
            query.executeUpdate();

            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

}
