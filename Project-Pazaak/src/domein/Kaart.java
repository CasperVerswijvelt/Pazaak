package domein;

import exceptions.InvalidCardException;

/**
 * the class used to create Card objects
 * @author goran
 */
public class Kaart {

    //Attributen
    private int waarde;
    private char type;
    private int prijs;

    //Constructor

    /**
     * Constructor for Card objects
     * @param waarde
     * @param type
     */
    public Kaart(int waarde, char type) {
        this(waarde, type, 0);
    }

    /**
     * Constructor for Card objects
     * @param waarde
     * @param type
     * @param prijs
     */
    public Kaart(int waarde, char type, int prijs) {
        controleerType(type);
        controleerWaarde(waarde);

        this.waarde = waarde;
        this.type = type;
        this.prijs = prijs;
    }

    //Methodes
    @Override
    public String toString() {
        return String.format("%s%d",
                type == '*' ? "+/-" : type,
                waarde);
    }

    /**
     * method that checks if 2 cards are the same or not
     * @param kaart
     * @return
     */
    public boolean equals(Kaart kaart) {
        if (kaart == null) {
            return false;
        } else if(kaart.type == type && kaart.waarde == waarde)
            return true;
        return false;
    }

    //Controle
    private void controleerWaarde(int waarde) {
        if (waarde < 0 || waarde > 20) {
            throw new InvalidCardException("De waarde van een kaart kan minimaal 0 en maximaal 20 zijn.");
        }
    }

    private void controleerType(char type) {
        if (type != '+' && type != '-' && type != '*' && type != 'T'&& type != 'D'&& type != 'W'&& type != 'C') {
            throw new InvalidCardException("Het type kan '+', '-', '*', 'T', 'D', 'W', of 'C' zijn, (" + type + " ingegeven)");
        }
    }

    //Getters & Setters

    /**
     * method to get the value of a card
     * @return
     */
    public int getWaarde() {
        return waarde;
    }

    /**
     * method to set the value of a card
     * @param waarde
     */
    public void setWaarde(int waarde) {
        controleerWaarde(waarde);
        this.waarde = waarde;
    }

    /**
     * method to get the type of a card
     * @return
     */
    public char getType() {
        return type;
    }

    /**
     * method to set the type of a card
     * @param type
     */
    public void setType(char type) {
        controleerType(type);
        this.type = type;
    }

    /**
     * method to get the price of a card
     * @return
     */
    public int getPrijs() {
        return prijs;
    }

    /**
     * method to set the price of a card
     * @param prijs
     */
    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }
    

}
