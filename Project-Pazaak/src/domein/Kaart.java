
/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package domein;

/**
 *
 * @author Casper
 */
public class Kaart {
    //Atributen
    private int waarde;
    private char type;
    
    //Constructor
    public Kaart(char type,int waarde) {
        controleerType(type);
        controleerWaarde(waarde);
        
        this.waarde = waarde;
        this.type = type;
    }
    
    //Methodes
    @Override
    public String toString(){
        return String.format("%s%d"
                ,type=='*'?"+/-":type
                ,waarde);
    }
    //Controle
    public void controleerWaarde(int waarde) {
        if(waarde<1||waarde>6)
            throw new IllegalArgumentException("De waarde van een kaart kan minimaal 1 en maximaal 6 zijn.");
    }
    public void controleerType(char type) {
        if(type!='+' && type!='-' && type!='*' )
            throw new IllegalArgumentException("Het type kan '+', '-' of '*' zijn.");
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
