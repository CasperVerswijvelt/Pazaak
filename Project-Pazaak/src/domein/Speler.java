package domein;

import exceptions.*;
import java.util.*;

/**
 * Class with all methods used on Player objects
 * @author goran
 */
public class Speler {

    //Attributen
    private List<Kaart> startStapel;
    private final String naam;
    private int krediet;
    private final int geboorteJaar;

    //Constructor

    /**
     * Constructor to make a Player object
     * @param naam
     * @param geboorteJaar
     * @param krediet
     * @param startStapel
     */
    public Speler(String naam, int geboorteJaar, int krediet, List<Kaart> startStapel) {

        controleerNaam(naam);
        controleerGeboorteJaar(geboorteJaar);

        this.naam = naam;
        this.geboorteJaar = geboorteJaar;
        this.krediet = krediet;

        this.startStapel = startStapel;

    }

    //Methodes
    @Override
    public String toString() {
        return String.format("%s, %d, %d", naam, geboorteJaar, krediet);
    }

    //Controle
    private void controleerNaam(String naam) {
        if(naam == null || naam.equals(""))
            throw new PlayerNameInvalidException("Leeg");
        
        int eerste = naam.charAt(0);
       
        if((eerste >47 && 
                eerste <58 )|| 
                naam.contains(" ") || 
                naam.matches("(?s).*\\p{Punct}.*"))
        throw new PlayerNameInvalidException();
        if (naam.length() < 3) 
            throw new PlayerNameInvalidException("Naam moet minstens 3 karakters lang zijn.");
        
        
    }

    private void controleerGeboorteJaar(int geboorteJaar) {
        int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
        int leeftijd = huidigJaar - geboorteJaar;
        if (leeftijd > 99 || leeftijd < 6) {
            throw new PlayerBirthInvalidException("Speler moet minstens 6 jaar of maximum 99 jaar oud worden dit jaar.");
        }
    }

    //Getters & Settesr

    /**
     * method that return the cards a player has
     * @return
     */
    public List<Kaart> geefStartStapel() {
        return (List) startStapel;
    }

    /**
     * method to get the name of a player
     * @return
     */
    public String getNaam() {
        return naam;
    }

    /**
     * method to get the credits of a player
     * @return
     */
    public int getKrediet() {
        return krediet;
    }

    /**
     * method to set a players credits
     * @param krediet
     */
    public void setKrediet(int krediet) {
        this.krediet = krediet;
    }

    /**
     * method to get the year of birth of a player
     * @return
     */
    public int getGeboorteJaar() {
        return geboorteJaar;
    }

    /**
     * method to set the cards a player has
     * @param startStapel
     */
    public void setStartStapel(List<Kaart> startStapel) {
        this.startStapel = startStapel;
    }

}
