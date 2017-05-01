/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package persistentie;

import domein.Speler;
import exceptions.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

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
        } catch (SQLException ex) {
            if (ex.getMessage().toLowerCase().contains("duplicate entry")) {
                throw new PlayerAlreadyExistsException("Player already exists");
            } else {
                throw new DatabaseException(ex);
            }
        }
    }

    public List<String> geefAlleSpelerNamen() {
        List<String> spelers = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT naam FROM ID222177_g37.Speler");
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
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

    public void veranderSpeler(String geselecteerdeSpeler, String nieuweNaam, int nieuweGebDat, int nieuwKrediet) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("UPDATE ID222177_g37.Speler SET `naam`=?, `geboortedatum`=?, `krediet`=? WHERE `naam`=?");
            query.setString(1, nieuweNaam);
            query.setInt(2, nieuweGebDat);
            query.setInt(3, nieuwKrediet);
            query.setString(4, geselecteerdeSpeler);
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public boolean valideerAdmin(String user, String password) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("SELECT pass, salt FROM ID222177_g37.admin WHERE user = ?");
            query.setString(1, user);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    String hash = rs.getString("pass");
                    String salt = rs.getString("salt");
                    return hash.equals(getHash(password + salt, "SHA-512"));

                }
            }
            query.close();

        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return false;

    }

    public String getHash(String toHash, String algorithm) {
        String hashValue = "";
        try {
            byte[] inputBytes = toHash.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputBytes);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        } catch (Exception e) {

        }
        return hashValue;
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }

    public void verwijderSpeler(String naam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("DELETE FROM ID222177_g37.Speler WHERE NAAM = ?");
            query.setString(1, naam);
            query.executeUpdate();
            
            query.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public void maakNieuweAdmin(String bestaandeAdminNaam, String bestaandeAdminUser, String nieuweAdminNaam, String nieuweAdminPass) {
        if (valideerAdmin(bestaandeAdminNaam, bestaandeAdminUser)) {
            try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
                PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g37.admin (user, pass, salt) VALUES (?,?,?)");
                String salt = generateSalt();
                String hash = getHash(nieuweAdminPass + salt, "SHA-512");

                query.setString(1, nieuweAdminNaam);
                query.setString(2, hash);
                query.setString(3, salt);
                query.executeUpdate();
                
                query.close();
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            }
        } else {
            throw new InvalidAdminCredentialsException();
        }
    }

}
