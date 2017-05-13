package domein;

import exceptions.*;
import java.util.*;
import persistentie.KaartMapper;
import persistentie.SpelerMapper;

/**
 * Class that connects to the PlayerMapper
 * @author goran
 */
public class SpelerEnKaartRepository {

    //Attributen
    private SpelerMapper sm;
    private KaartMapper km;

    //Constructor

    /**
     * creates a repository object
     */
    public SpelerEnKaartRepository() {
        sm = new SpelerMapper();
        km = new KaartMapper();
    }

    //Methodes

    /**
     * creates a new player
     * @param naam
     * @param geboorteDatum
     */
    public void maakNieuweSpelerAan(String naam, int geboorteDatum) {
        Speler speler = new Speler(naam, geboorteDatum, 0, null);
        this.voegToe(speler);
    }

    /**
     * checks if a player already exists
     * @param naam
     * @return
     */
    public boolean bestaat(String naam) {
        try{
            sm.geefSpeler(naam);
            return true;
        } catch(PlayerDoesntExistException e) {
            return false;
        }
    }

    /**
     * return all the players in the database
     * @return
     */
    public List<String> geefSpelersLijst() {
        return sm.geefAlleSpelerNamen();
    }

    /**
     * retuns name , year of birth and the credits of a player
     * @param naam
     * @return
     */
    public String[] geefSpelerInfo(String naam) {
        Speler speler = sm.geefSpeler(naam);
       
        String info[] = new String[3];

        info[0] = speler.getNaam();
        info[1] = speler.getKrediet() + "";
        info[2] = speler.getGeboorteJaar() + "";

        return info;
    }

    private void voegToe(Speler speler) {

        sm.voegToe(speler);

    }

    /**
     * returns a player object
     * @param naam
     * @return
     */
    public Speler geefSpeler(String naam) {
        Speler speler = sm.geefSpeler(naam);
        speler.setStartStapel(km.geefStartStapel(naam));
        return speler;
    }

    /**
     * return all the cards a player has
     * @param naam
     * @return
     */
    public List<Kaart> geefStartStapel(String naam) {
        if (bestaat(naam)) {
            return km.geefStartStapel(naam);
        } else {
            throw new PlayerDoesntExistException();
        }
    }

    /**
     * saves the credits
     * @param speler
     */
    public void slaKredietOp(Speler speler) {
        sm.slaKredietOp(speler);
    }

    /**
     * returns all cards the player hasn't bought
     * @param naam
     * @return
     */
    public List<Kaart> geefNogNietGekochteKaarten(String naam) {
        if (bestaat(naam)) {
            return km.geefNogNietGekochteKaarten(naam);
        } else {
            throw new PlayerDoesntExistException();
        }
    }

    /**
     * adds card to owned cards and changes the credits of a player
     * @param naam
     * @param kaart
     */
    public void koopKaart(String naam, Kaart kaart) {
        Speler speler = geefSpeler(naam);
        int prijs = kaart.getPrijs();
        if (speler.getKrediet() >= prijs) {
            km.voegStartstapelKaartToe(naam, kaart);
            speler.setKrediet(speler.getKrediet() - prijs);
            slaKredietOp(speler);

        } else {
            throw new InsufficientBalanceException("Insufficient balance");
        }

    }

    /**
     * returns al bought cards
     * @param naam
     * @return
     */
    public List<Kaart> geefAangekochteKaarten(String naam) {
        return km.geefAangekochteKaarten(naam);
    }
    
    /**
     * returns the prices of the cards
     * @return
     */
    public List<Integer> geefPrijzenKaarten() {
        return km.geefPrijzenKaarten();
    }

    /**
     *Changes the information of given player in the database, all saved games and bought cards remain connected to the player if name is changed.
     * @param spelerNaam
     * @param nieuweNaam
     * @param nieuweGebDat
     * @param nieuwKrediet
     */
    public void veranderSpeler(String spelerNaam, String nieuweNaam, int nieuweGebDat, int nieuwKrediet) {
        Speler speler = new Speler(nieuweNaam,nieuweGebDat, 0, null);
        sm.veranderSpeler(spelerNaam, nieuweNaam, nieuweGebDat, nieuwKrediet);
    }
    
    /**
     * method to to validate the admin login
     * @param user
     * @param password
     * @return
     */
    public boolean valideerAdmin(String user, String password) {
        return sm.valideerAdmin(user, password);
    }

    /**
     *Removes player with specified name from the database, if it exists
     * @param naam
     */
    public void verwijderSpeler(String naam) {
        sm.verwijderSpeler(naam);
    }

    /**
     *Create a new admin user with specified credentials, requires credentials of existing admin user
     * @param bestaandeAdminNaam 
     * @param bestaandeAdminPass
     * @param nieuweAdminNaam
     * @param nieuweAdminPass
     */
    public void maakNieuweAdmin(String bestaandeAdminNaam, String bestaandeAdminPass, String nieuweAdminNaam, String nieuweAdminPass) {
        sm.maakNieuweAdmin(bestaandeAdminNaam, bestaandeAdminPass, nieuweAdminNaam, nieuweAdminPass);
    }

    /**
     *Adds specified card to specified player's starting deck
     * @param naam
     * @param kaart
     */
    public void voegStartstapelkaartToe(String naam, Kaart kaart) {
        km.voegStartstapelKaartToe(naam, kaart);
    }

    /**
     *Takes specified card from specified player's starting deck
     * @param naam
     * @param kaart
     */
    public void neemStartstapelkaartWeg(String naam, Kaart kaart) {
        km.neemStartstapelkaartWeg(naam, kaart);
    }
    

}
