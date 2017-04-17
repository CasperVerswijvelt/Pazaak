package domein;

public class Kaart {

    //Attributen
    private int waarde;
    private char type;

    //Constructor
    public Kaart(int waarde, char type) {
        controleerType(type);
        controleerWaarde(waarde);

        this.waarde = waarde;
        this.type = type;
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
        if (waarde < 1 || waarde > 10) {
            throw new IllegalArgumentException("De waarde van een kaart kan minimaal 1 en maximaal 10 zijn.");
        }
    }

    private void controleerType(char type) {
        if (type != '+' && type != '-' && type != '*') {
            throw new IllegalArgumentException("Het type kan '+', '-' of '*' zijn.");
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

}
