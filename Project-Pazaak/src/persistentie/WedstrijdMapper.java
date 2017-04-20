/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Kaart;
import domein.Speler;
import domein.Wedstrijd;
import exceptions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            
            if(bestaatWedstrijd(wedstrijdNaam))
                throw new GameAlreadyExistsException("ERROR SAVING GAME");

            //Wedstrijd
            PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g37.Wedstrijd (naam, spelerAanBeurt)"
                    + "VALUES (?, ?)");
            query.setString(1, wedstrijdNaam);
            query.setString(2, wedstrijd.geefSpelerAanBeurt());
            query.executeUpdate();

            //SpelerDeelnames
            query = conn.prepareStatement("INSERT INTO ID222177_g37.WedstrijdDeelname (score, SpelerNaam, WedstrijdNaam)"
                    + "VALUES (?, ?, ?),(?, ?, ?)");
            query.setInt(1, wedstrijd.geefTussenstand()[0]);
            query.setString(2, wedstrijd.geefSpelers().get(0).getNaam());
            query.setString(3, wedstrijdNaam);
            query.setInt(4, wedstrijd.geefTussenstand()[1]);
            query.setString(5, wedstrijd.geefSpelers().get(1).getNaam());
            query.setString(6, wedstrijdNaam);
            query.executeUpdate();

            //Wedstrijdkaarten
            for (int i = 0; i < 2; i++) {
                List<Kaart> wedstrijdStapel = wedstrijd.geefWedstrijdStapel(i);
                for (int j = 0; j < wedstrijdStapel.size(); j++) {
                    query = conn.prepareStatement("INSERT INTO ID222177_g37.WedstrijdKaart (KaartTypeId, SpelerNaam, WedstrijdNaam)"
                            + "VALUES (?, ?, ?)");
                    query.setInt(1, km.geefKaartId(wedstrijdStapel.get(j)));
                    query.setString(2, wedstrijd.geefSpelers().get(i).getNaam());
                    query.setString(3, wedstrijdNaam);
                    query.executeUpdate();
                }

            }

        } catch (SQLException ex) {
            throw new GameSaveDatabaseException();
        }
    }
    
    public boolean bestaatWedstrijd(String wedstrijdNaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            //Wedstrijd
            PreparedStatement query = conn.prepareStatement("SELECT naam FROM ID222177_g37.Wedstrijd WHERE naam = ?");
            query.setString(1, wedstrijdNaam);
            ResultSet rs = query.executeQuery();
            
            return rs.next();

        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public Wedstrijd laadWedstrijd(String wedstrijdNaam) {
        try {
            if(!bestaatWedstrijd(wedstrijdNaam))
                throw new GameDoesntExistException("ERROR LOADING GAME");
            
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);

            HashMap<String, Integer> scoresMap = new HashMap<>();
            HashMap<String, List<Kaart>> kaartenMap = new HashMap<>();
            String beginnendeSpeler = "";
            List<String> keys = new ArrayList<>();

            //Eerste query (scores en spelernamen)
            PreparedStatement query = conn.prepareStatement("SELECT score, SpelerNaam FROM ID222177_g37.WedstrijdDeelname WHERE WedstrijdNaam = ?");
            query.setString(1, wedstrijdNaam);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                scoresMap.put(rs.getString("SpelerNaam"), rs.getInt("score"));
            }

            //Tweede query (speler eerst aan beurt)
            query = conn.prepareStatement("SELECT spelerAanBeurt FROM ID222177_g37.Wedstrijd where naam = ?");
            query.setString(1, wedstrijdNaam);
            rs = query.executeQuery();

            while (rs.next()) {
                beginnendeSpeler = rs.getString("spelerAanBeurt");
            }

            //Derde query (kaartenlijst en spelernamen)
            query = conn.prepareStatement("SELECT waarde, type, prijs FROM ID222177_g37.WedstrijdKaart LEFT JOIN ID222177_g37.KaartType ON ID222177_g37.WedstrijdKaart.KaartTypeId = ID222177_g37.KaartType.id WHERE SpelerNaam = ? AND WedstrijdNaam = ? ");

            Iterator it = scoresMap.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, Integer> pair = (Map.Entry) it.next();
                String spelerNaam = pair.getKey();
                query.setString(1, spelerNaam);
                query.setString(2, wedstrijdNaam);
                rs = query.executeQuery();

                List<Kaart> kaarten = new ArrayList<>();
                while (rs.next()) {
                    kaarten.add(new Kaart(rs.getInt("waarde"), rs.getString("type").charAt(0), rs.getInt("prijs")));
                }
                kaartenMap.put(spelerNaam, kaarten);
                keys.add(spelerNaam);
            }

           

            //Vierde query (wedstrijdkaarten verwijderen uit databank)
            for (String speler : keys) {

                query = conn.prepareStatement("DELETE FROM ID222177_g37.WedstrijdKaart WHERE SpelerNaam = ? AND WedstrijdNaam = ?");
                query.setString(1, speler);
                query.setString(2, wedstrijdNaam);
                query.executeUpdate();
                
                

            }
            
            //Vijfde query (wedstrijdDeelnames verwijderen)
            query = conn.prepareStatement("DELETE FROM ID222177_g37.WedstrijdDeelname WHERE WedstrijdNaam = ?");
            query.setString(1, wedstrijdNaam);
            query.executeUpdate();
            
            //Zesde query (wedstrijd zelf verwijderen)
            query = conn.prepareStatement("DELETE FROM ID222177_g37.Wedstrijd WHERE naam = ?");
            query.setString(1, wedstrijdNaam);
            query.executeUpdate();
            
             //Wedstrijd aanmaken met gegeven gegevens
            return new Wedstrijd(sm.geefSpeler(keys.get(0)), sm.geefSpeler(keys.get(1)), kaartenMap.get(keys.get(0)), kaartenMap.get(keys.get(1)), beginnendeSpeler, scoresMap.get(keys.get(0)), scoresMap.get(keys.get(1)));

        } catch (SQLException ex) {
            throw new GameLoadDatabaseException(ex);
        }

    }
    
    public String[][] geefWedstrijdenOverzicht() {
        List<String[]> lijst= new ArrayList<>();
        String[][] res = new String[1][1];
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);

            PreparedStatement query = conn.prepareStatement("SELECT WedstrijdNaam, SpelerNaam, score FROM ID222177_g37.WedstrijdDeelname LEFT JOIN ID222177_g37.Wedstrijd ON ID222177_g37.WedstrijdDeelname.WedstrijdNaam = ID222177_g37.Wedstrijd.naam  ORDER BY WedstrijdNaam");
            ResultSet rs = query.executeQuery();
            
            while (rs.next()) {
                String[] info = new String[3];
                info[0] = rs.getString("WedstrijdNaam");
                info[1] = rs.getString("SpelerNaam");
                info[2] = rs.getInt("score")+"";
                lijst.add(info);
            }
            
            int size = lijst.size();
            
            if(size%2 != 0) 
                throw new IncorrectEntriesInDatabaseException("Not each game has exactly 2 players");
            
            res = new String[size/2][5];
            
            for(int i = 0; i< size; i+=2) {
                //WedstrijdNaam
                res[i/2][0] = lijst.get(i)[0];
                
                //Speler1
                res[i/2][1] = lijst.get(i)[1];
                res[i/2][2] = lijst.get(i)[2];
                
                //Speler2
                res[i/2][3] = lijst.get(i+1)[1];
                res[i/2][4] = lijst.get(i+1)[2];
            }
        }catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return res;
    }

}
