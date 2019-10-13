package domein;

import java.util.List;

public interface ISpelerEnKaartRepository {

    void maakNieuweSpelerAan(String naam, int geboorteDatum);

    /**
     * checks if a player already exists
     * @param naam
     * @return
     */
    boolean bestaat(String naam);

    /**
     * return all the players in the database
     * @return
     */
    List<String> geefSpelersLijst();

    /**
     * retuns name , year of birth and the credits of a player
     * @param naam
     * @return
     */
    String[] geefSpelerInfo(String naam);

    /**
     * returns a player object
     * @param naam
     * @return
     */
    Speler geefSpeler(String naam);

    /**
     * return all the cards a player has
     * @param naam
     * @return
     */
    List<Kaart> geefStartStapel(String naam);

    /**
     * saves the credits
     * @param speler
     */
    void slaKredietOp(Speler speler);

    /**
     * returns all cards the player hasn't bought
     * @param naam
     * @return
     */
    List<Kaart> geefNogNietGekochteKaarten(String naam);

    /**
     * adds card to owned cards and changes the credits of a player
     * @param naam
     * @param kaart
     */
    void koopKaart(String naam, Kaart kaart);

    /**
     * returns al bought cards
     * @param naam
     * @return
     */
    List<Kaart> geefAangekochteKaarten(String naam);

    /**
     * returns the prices of the cards
     * @return
     */
    List<Integer> geefPrijzenKaarten();

    /**
     *Changes the information of given player in the database, all saved games and bought cards remain connected to the player if name is changed.
     * @param spelerNaam
     * @param nieuweNaam
     * @param nieuweGebDat
     * @param nieuwKrediet
     */
    void veranderSpeler(String spelerNaam, String nieuweNaam, int nieuweGebDat, int nieuwKrediet);

    /**
     * method to to validate the admin login
     * @param user
     * @param password
     * @return
     */
    boolean valideerAdmin(String user, String password);

    /**
     *Removes player with specified name from the database, if it exists
     * @param naam
     */
    void verwijderSpeler(String naam);

    /**
     *Create a new admin user with specified credentials, requires credentials of existing admin user
     * @param bestaandeAdminNaam
     * @param bestaandeAdminPass
     * @param nieuweAdminNaam
     * @param nieuweAdminPass
     */
    void maakNieuweAdmin(String bestaandeAdminNaam, String bestaandeAdminPass, String nieuweAdminNaam, String nieuweAdminPass);

    /**
     *Adds specified card to specified player's starting deck
     * @param naam
     * @param kaart
     */
    void voegStartstapelkaartToe(String naam, Kaart kaart);

    /**
     *Takes specified card from specified player's starting deck
     * @param naam
     * @param kaart
     */
    void neemStartstapelkaartWeg(String naam, Kaart kaart);
}
