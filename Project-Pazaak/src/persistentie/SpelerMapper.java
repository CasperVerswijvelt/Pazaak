/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package persistentie;

import domein.Speler;
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
public class SpelerMapper {

    public Speler geefSpeler(String naam) {
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g37.Speler WHERE naam = ?");
            query.setString(1, naam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    String naam2 = rs.getString("naam");
                    int geboortedatum = rs.getInt("geboortedatum");
                    int krediet = rs.getInt("krediet");
                    return new Speler(naam2, geboortedatum, krediet, null);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        throw new PlayerDoesntExistException();
    }

    public void voegToe(Speler speler) {

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g37.Speler (naam, geboortedatum)"
                    + "VALUES (?, ?)");
            query.setString(1, speler.getNaam());
            query.setInt(2, speler.getGeboorteJaar());
            query.executeUpdate();
        }catch (SQLException ex) {
            if(ex.getMessage().toLowerCase().contains("duplicate entry"))
                throw new PlayerAlreadyExistsException("Player already exists");
            else
                throw new DatabaseException(ex);
        }
    }
    
    
    

    public List<String> geefAlleSpelerNamen() {
        List<String> spelers = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT naam FROM ID222177_g37.Speler");
            try (ResultSet rs = query.executeQuery()) {
                while(rs.next()) {
                    String naam = rs.getString("naam");
                    spelers.add(naam);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return spelers;
    }
    
    public void slaKredietOp(Speler speler) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("UPDATE ID222177_g37.Speler SET krediet = ? WHERE naam = ?");
            query.setInt(1, speler.getKrediet());
            query.setString(2, speler.getNaam());
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
    
    
    
}
