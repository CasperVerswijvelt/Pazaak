package domein;

import exceptions.NoPlayersAvailableException;
import java.util.*;
import persistentie.KaartMapper;
import persistentie.SpelerMapper;

/**
 * @author Casper
 */
public class DomeinController {

    //Attributen
    private final SpelerRepository spelerRepo;
    private final WedstrijdRepository wedstrijdRepo;
    private Wedstrijd wedstrijd;
    private final List<Speler> geselecteerdeSpelers;
    private final List<Kaart> geselecteerdeKaarten;
    private Speler geselecteerdeSpelerWedstrijdstapel;

    //Constructor

    /**
     *
     */
    public DomeinController() {
        this.spelerRepo = new SpelerRepository();
        this.wedstrijdRepo = new WedstrijdRepository();
        this.geselecteerdeSpelers = new ArrayList<>();
        this.geselecteerdeKaarten = new ArrayList<>();
    }

    //Methodes

    /**
     *Makes a new player with the given parameters and saves it to the database, if it doesn't exist already and all requirements are met.
     * @param naam
     * @param geboorteJaar
     */
    public void maakNieuweSpelerAan(String naam, int geboorteJaar) {
        spelerRepo.maakNieuweSpelerAan(naam, geboorteJaar);
    }

    /**
     *Checks if a player with given name exists in the database
     * @param naam
     * @return
     */
    public boolean spelerBestaat(String naam) {
        return spelerRepo.bestaat(naam);
    }

    /**
     *Gives the name, credit, year of birth of given playername, if it exists in the database
     * @param naam
     * @return
     */
    public String[] geefSpelerInfo(String naam) {
        return spelerRepo.geefSpelerInfo(naam);
    }

    /**
     *Gives a list of playernames that are saved in the database
     * @return
     */
    public List<String> geefAlleSpelerNamen() {
        return spelerRepo.geefSpelersLijst();
    }

    /**
     *Selects a player for a new game, note: you must select 2 players to create a game
     * @param naam
     */
    public void selecteerSpeler(String naam) {
        Speler speler = spelerRepo.geefSpeler(naam);

        this.geselecteerdeSpelers.add(speler);

        if (this.geselecteerdeSpelers.size() > 2) {
            this.geselecteerdeSpelers.remove(0);
        }
    }

    /**
     *Gives a list of players that were selected with selecteerSpeler(String naam), note: will only give last 2 selected players
     * @return
     */
    public List<String> geefGeselecteerdeSpelers() {
        List<String> lijst = new ArrayList<>();

        for (Speler element : geselecteerdeSpelers) {
            lijst.add(element.getNaam());
        }

        return lijst;
    }

    /**
     *Makes a new game with selected players, if 2 players are selected
     */
    public void maakNieuweWedstrijd() {
        if (geselecteerdeSpelers.size() < 2) {
            throw new NoPlayersAvailableException();
        }
        
        this.wedstrijd = new Wedstrijd(geselecteerdeSpelers.get(0), geselecteerdeSpelers.get(1));

        geselecteerdeSpelers.clear();
    }

    /**
     *Gives the start deck of the player that was selected to make a new game deck
     * @return
     */
    public String[][] geefStartStapel() {

        return kaartenAlsString(geselecteerdeSpelerWedstrijdstapel.geefStartStapel());
    }

    /**
     *Gives the start deck of the player in the parameter, if the player exists
     * @param naam
     * @return
     */
    public String[][] geefStartStapel(String naam) {
        return kaartenAlsString(spelerRepo.geefStartStapel(naam));
    }

    /**
     *Gives the amount of players that are saved in the database
     * @return
     */
    public int geefAantalSpelers() {
        return spelerRepo.geefSpelersLijst().size();
    }

    /**
     *Gives the players from the current game that have not yet created a game deck
     * @return
     */
    public List<String> geefSpelersZonderWedstrijdStapel() {

        List<Speler> spelers = wedstrijd.geefSpelersZonderWedstrijdStapel();
        List<String> res = new ArrayList<>();

        for (Speler element : spelers) {
            res.add(element.getNaam());
        }
        return res;
    }

    /**
     *Select a player for which to create a game deck
     * @param naam
     */
    public void selecterSpelerWedstrijdStapel(String naam) {
        this.geselecteerdeSpelerWedstrijdstapel = wedstrijd.geefSpeler(naam);
    }


    /**
     * Possible doesn't work, gives card from start deck of the selected player to make a game deck, that have not yet been selected
     * @return
     */
    public String[][] geefNietGeselecteerdeKaarten() {
        List<Kaart> nietGeselecteerdeKaarten = geselecteerdeSpelerWedstrijdstapel.geefStartStapel();

        for (Kaart element : geselecteerdeKaarten) {
            nietGeselecteerdeKaarten.remove(element);
        }

        return kaartenAlsString(nietGeselecteerdeKaarten);

    }

    /**
     * Select a card for the current selected player to create a game deck for
     * @param kaart
     */
    public void selecteerKaart(String[] kaart) {
        this.geselecteerdeKaarten.add(stringAlsKaart(kaart));
    }

    /**
     *Creates the game deck for the current selected player to create a game deck for, with the selected cards
     */
    public void maakWedstrijdStapel() {
        wedstrijd.maakWedstrijdstapel(geselecteerdeKaarten, geselecteerdeSpelerWedstrijdstapel);
        geselecteerdeKaarten.clear();
        geselecteerdeSpelerWedstrijdstapel = null;
    }
    
    /**
     *Gives the game deck of the first (index =0) or second (index = 1) player in the game
     * @param index
     * @return
     */
    public String[][] geefWedstrijdStapel(int index) {
        return kaartenAlsString(wedstrijd.geefWedstrijdStapel(index));
    }

    /**
     *Creates a new set for the current game
     */
    public void maakNieuweSet() {
        wedstrijd.maakNieuweSet();
    }

    /**
     *Gives the name of the player who's currently at turn in the current set
     * @return
     */
    public String geefSpelerAanBeurt() {
        return wedstrijd.geefSpelerAanBeurt();
    }

    /**
     *Changes the credit of the player with given name and saves it into the database, note: you can only change a players credit if he is participating in the current game
     * @param naam
     * @param aantal
     */
    public void veranderKrediet(String naam, int aantal) {
        Speler speler = wedstrijd.geefSpeler(naam);
        speler.setKrediet(speler.getKrediet() + aantal);
        spelerRepo.slaKredietOp(speler);
    }

    /**
     *Deals a card from the set deck to the player at turn
     */
    public void deelKaartUit() {
        wedstrijd.deelKaartUit();
    }

    /**
     *Gives the gameboard of the player who is currently at turn in the set
     * @return
     */
    public String[][] geefSpelBord() {
        return kaartenAlsString(wedstrijd.geefSpelBord());
    }

    /**
     *Gives the score of the player who is currently at turn in the set 
     * @return
     */
    public int geefScore() {
        return wedstrijd.geefScore();
    }

    /**
     *Gives the possible game actions of the player currently at turn in the set
     * @return
     */
    public List<String> geefMogelijkeActies() {
        return wedstrijd.geefMogelijkeActies();
    }

    /**
     *Ends the turn of the player currently at turn in the set
     */
    public void eindigBeurt() {
        wedstrijd.eindigBeurt();
    }

    /**
     *Freezes the board of the player currently at turn in the set
     */
    public void bevriesBord() {
        wedstrijd.bevriesBord();
    }

    /**
     *Gives the current game deck of the player currently at turn in the set
     * @return
     */
    public String[][] geefWedstrijdStapel() {
        return kaartenAlsString(wedstrijd.geefWedstrijdStapel());
    }
    
    /**
     * Uses a card of the game deck for the player currently at turn in the set. Method requires the array of strings of the original card, and if the card is +/- or 1+/-2 the requested type and/or value
     * @param kaart
     * @param gewensteWaarde
     * @param gewenstType
     */
    public void gebruikWedstrijdKaart(String[] kaart,int gewensteWaarde, char gewenstType) {
        wedstrijd.gebruikWedstrijdKaart(stringAlsKaart(kaart), gewensteWaarde, gewenstType);
    }

    /**
     *Checks if the set is done
     * @return
     */
    public boolean setIsKlaar() {
        return wedstrijd.setIsKlaar();
    }

    /**
     *Gives the winner (if not tie) of the current set
     * @return
     */
    public String geefSetUitslag() {
        return wedstrijd.geefSetUitslag();
    }

    /**
     *Gives the winner of the game, if there is a winner
     * @return
     */
    public String geefWinnaar() {
        return wedstrijd.geefWinnaar().getNaam();
    }

    /**
     *Checks if the game is over (a player has 3 wins)
     * @return
     */
    public boolean wedstrijdIsKlaar() {
        return wedstrijd.isKlaar();
    }

    

    /**
     *Returns the players participating in the current game
     * @return
     */
    public String[] geefWedstrijdSpelers() {
        List<Speler> spelers = wedstrijd.geefSpelers();
        String[] res = new String[spelers.size()];
        for (int i = 0; i < spelers.size(); i++) {
            res[i] = spelers.get(i).getNaam();
        }

        return res;
    }

    /**
     *Gives the amount of wins each player in the current game hast
     * @return
     */
    public int[] geefWedstrijdTussenstand() {
        return wedstrijd.geefTussenstand();
    }

    /**
     *Checks if the set is won and if yes, highers the amount of wins for that player in the game
     */
    public void registreerAantalWins() {
        wedstrijd.registreerAantalWins();
    }

    /**
     *Buys given card for the player with given name, if the player exists, has enough balance, and has not already bought the card
     * @param naam
     * @param kaart
     */
    public void koopKaart(String naam, String[] kaart) {
        spelerRepo.koopKaart(naam, stringAlsKaart(kaart));
    }

    /**
     *Saves the current game in the database with given name
     * @param naam
     */
    public void slaWedstrijdOp(String naam) {
       wedstrijdRepo.slaWedstrijdOp(wedstrijd, naam);
    }

    /**
     *Loads the game with given name (if exists) and removes it from the database
     * @param naam
     */
    public void laadWedstrijd(String naam) {
        this.wedstrijd = wedstrijdRepo.laadWedstrijd(naam);
    }

    /**
     *Gives a list of all games saved in the database, with participating players and their score
     * @return
     */
    public String[][] geefWedstrijdenOverzicht() {
        return wedstrijdRepo.geefWedstrijdenOverzicht();
    }
    
    /**
     *Returns the prices of the card types in the databse
     * @return
     */
    public List<Integer> geefPrijzenKaarten() {
        return spelerRepo.geefPrijzenKaarten();
    }


    /**
     *Gives the cards that a player has not yet bought
     * @param naam
     * @return
     */
    public String[][] geefNogNietGekochteKaarten(String naam) {
        return kaartenAlsString(spelerRepo.geefNogNietGekochteKaarten(naam));
    }
    
    /**
     *Gives the cards that a player has already bought
     * @param naam
     * @return
     */
    public String[][] geefAangekochteKaarten(String naam) {
        return kaartenAlsString(spelerRepo.geefAangekochteKaarten(naam));
    }

    
    //Utilities

    private Kaart stringAlsKaart(String[] kaart) {
        return new Kaart(Integer.parseInt(kaart[1]), kaart[0].charAt(0), Integer.parseInt(kaart[2]));
    }
    private String[][] kaartenAlsString(List<Kaart> kaarten) {
        String[][] res = new String[kaarten.size()][3];

        for (int i = 0; i < kaarten.size(); i++) {
            res[i][0] = kaarten.get(i).getType() + "";
            res[i][1] = kaarten.get(i).getWaarde() + "";
            res[i][2] = kaarten.get(i).getPrijs() + "";
        }
        return res;
    }
    
    

}
