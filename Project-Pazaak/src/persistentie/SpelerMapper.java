/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package persistentie;

import domein.Speler;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Casper
 */
public class SpelerMapper {

    public Speler geefSpeler(String naam) {
        Speler speler = null;
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g37.Speler WHERE naam = ?");
            query.setString(1, naam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    String naam2 = rs.getString("naam");
                    int geboortedatum = rs.getInt("geboortedatum");
                    speler = new Speler(naam2,geboortedatum);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return speler;
    }
    
public void voegToe(Speler speler) {

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g37.Speler (naam, geboortedatum)"
                    + "VALUES (?, ?)");
            query.setString(1, speler.getNaam());
            query.setInt(2, speler.getGeboorteDatum());

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}