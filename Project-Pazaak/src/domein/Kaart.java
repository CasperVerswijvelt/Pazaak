package domein;

import exceptions.InvalidCardException;

public class Kaart {

    //Attributen
    private int waarde;
    private char type;
    private int prijs;

    //Constructor
    public Kaart(int waarde, char type) {
        this(waarde, type, 0);
    }

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

    public boolean equals(Kaart kaart) {
        if (kaart == null) {
            return false;
        } else if(kaart.type == type && kaart.waarde == waarde)
            return true;
        return false;
    }

    //Controle
    private void controleerWaarde(int waarde) {
        if (waarde < 0 || waarde > 10) {
            throw new InvalidCardException("De waarde van een kaart kan minimaal 0 en maximaal 10 zijn.");
        }
    }

    private void controleerType(char type) {
        if (type != '+' && type != '-' && type != '*' && type != 'T'&& type != 'D'&& type != 'W'&& type != 'C') {
            throw new InvalidCardException("Het type kan '+', '-', '*', 'T', 'D', 'W', of 'C' zijn, (" + type + " ingegeven)");
        }
    }

    //Getters & Setters
    public int getWaarde() {
        return waarde;
    }

    public void setWaarde(int waarde) {
        controleerWaarde(waarde);
        this.waarde = waarde;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        controleerType(type);
        this.type = type;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }
    

}
