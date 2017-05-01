/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Kaart;
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
public class KaartMapper {

    public void voegKaartTypeToe(char type, int waarde, int prijs) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g37.KaartType (type, waarde, prijs)"
                    + "VALUES (?, ?, ?)");
            query.setString(1, type + "");
            query.setInt(2, waarde);
            query.setInt(3, prijs);
            query.executeUpdate();

            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public List<Kaart> geefAlleKaartenInWInkel() {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g37.KaartType WHERE prijs <> 0");
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    int waarde = rs.getInt("waarde");
                    int prijs = rs.getInt("prijs");
                    char type = rs.getString("type").charAt(0);

                    kaarten.add(new Kaart(waarde, type, prijs));
                }
            }
            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        
        return kaarten;
    }

    public List<Integer> geefPrijzenKaarten() {
        List<Integer> prijzen = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT DISTINCT type, prijs FROM ID222177_g37.KaartType WHERE prijs <> 0 ORDER BY id");
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    prijzen.add(rs.getInt("prijs"));
                }
            }
            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return prijzen;
    }

    public List<Kaart> geefNogNietGekochteKaarten(String naam) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g37.KaartType WHERE id NOT IN(SELECT id FROM ID222177_g37.Kaart WHERE naam = ? ) AND prijs <> 0");
            query.setString(1, naam);
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    int waarde = rs.getInt("waarde");
                    int prijs = rs.getInt("prijs");
                    char type = rs.getString("type").charAt(0);

                    kaarten.add(new Kaart(waarde, type, prijs));
                }
            }
            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return kaarten;
    }

    public void voegStartstapelKaartToe(String naam, Kaart kaart) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            int kaartID = geefKaartId(kaart);
            PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g37.Kaart (naam, id) "
                    + "VALUES (?, ?)");
            query.setString(1, naam);
            query.setInt(2, kaartID);
            query.executeUpdate();

            query.close();
        } catch (SQLException ex) {
            if (ex.getMessage().toLowerCase().contains("duplicate entry")) {
                throw new CardAlreadyBoughtException(ex);
            }
            throw new DatabaseException(ex);
            
        }
    }

    public int geefKaartId(Kaart kaart) {
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT id FROM ID222177_g37.KaartType WHERE type = ? AND waarde = ? AND prijs = ?");
            query.setString(1, kaart.getType() + "");
            query.setInt(2, kaart.getWaarde());
            query.setInt(3, kaart.getPrijs());
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt("id");

                }
                query.close();
                throw new CardDoesntExistException("Card doesnt exist");
            }
            
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }

    }

    public List<Kaart> geefAangekochteKaarten(String naam) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g37.KaartType WHERE id IN(SELECT id FROM ID222177_g37.Kaart WHERE naam = ? )");
            query.setString(1, naam);
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    int waarde = rs.getInt("waarde");
                    int prijs = rs.getInt("prijs");
                    char type = rs.getString("type").charAt(0);

                    kaarten.add(new Kaart(waarde, type, prijs));
                }
            }
            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return kaarten;
    }

    public List<Kaart> geefStartStapel(String naam) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g37.KaartType WHERE id IN(SELECT id FROM ID222177_g37.Kaart WHERE naam = ? ) OR prijs = 0");
            query.setString(1, naam);
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    int waarde = rs.getInt("waarde");
                    int prijs = rs.getInt("prijs");
                    char type = rs.getString("type").charAt(0);

                    kaarten.add(new Kaart(waarde, type, prijs));
                }
            }
            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return kaarten;
    }

    //Kan gebruikt worden indien er in de databse iets fout is met de KaartType tabel
    public void bouwKaartTypeDatabase() {
        //KaarType in DB leegmaken
        leegAlleKaartTypes();

        //Alle aankoopbare kaarten toevoegen
        for (int i = 1; i <= 6; i++) {
            voegKaartTypeToe('+', i, 5);
        }
        for (int i = 1; i <= 6; i++) {
            voegKaartTypeToe('-', i, 5);
        }
        for (int i = 1; i <= 6; i++) {
            voegKaartTypeToe('*', i, 10);
        }
        voegKaartTypeToe('T', 1, 20);
        voegKaartTypeToe('D', 0, 30);
        voegKaartTypeToe('W', 1, 50);
        voegKaartTypeToe('W', 2, 50);
        voegKaartTypeToe('C', 1, 100);

        //StartStapel toevoegen met prijs 0 - startStapel
        voegKaartTypeToe('+', 2, 0);
        voegKaartTypeToe('+', 4, 0);
        voegKaartTypeToe('+', 5, 0);
        voegKaartTypeToe('+', 6, 0);
        voegKaartTypeToe('-', 1, 0);
        voegKaartTypeToe('-', 2, 0);
        voegKaartTypeToe('-', 3, 0);
        voegKaartTypeToe('-', 5, 0);
        voegKaartTypeToe('*', 3, 0);
        voegKaartTypeToe('*', 1, 0);

    }

    public void leegAlleAangekochteKaarten() {
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("truncate ID222177_g37.Kaart");
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    //MAG ALLEEN AANGEROEPEN WORDEN DOOR bouwKaartTypeDatabase()
    private void leegAlleKaartTypes() {
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0");
            query.executeUpdate();
            query = conn.prepareStatement("truncate ID222177_g37.KaartType");
            query.executeUpdate();
            query = conn.prepareStatement("ALTER TABLE ID222177_g37.KaartType  AUTO_INCREMENT = 1");
            query.executeUpdate();
            query = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1");
            query.executeUpdate();
            
            query.close();

        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public void neemStartstapelkaartWeg(String naam, Kaart kaart) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            int kaartID = geefKaartId(kaart);
            PreparedStatement query = conn.prepareStatement("DELETE FROM ID222177_g37.Kaart WHERE naam = ? AND id = ?");
            query.setString(1, naam);
            query.setInt(2, kaartID);
            query.executeUpdate();
            
            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
