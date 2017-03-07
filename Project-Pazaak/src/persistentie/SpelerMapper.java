/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package persistentie;

import domein.Speler;
import java.sql.DriverManager;

/**
 *
 * @author Casper
 */
public class SpelerMapper {
    public Speler haalSpelerOp(String naam){
        Connection con = DriverManager.getConnection(Connectie.JDBC_URL);
    }
}