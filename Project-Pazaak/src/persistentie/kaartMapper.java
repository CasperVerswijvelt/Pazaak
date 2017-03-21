/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package persistentie;

import domein.Kaart;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Casper
 */
public class kaartMapper {

    public void voegKaartToe(Kaart kaart) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g37.kaart (type, waarde)"
                    + "VALUES (?, ?)");
            query.setString(1, kaart.getType()+"");
            query.setInt(2, kaart.getWaarde());
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
