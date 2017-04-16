package domein;

import java.util.*;

public class Speler {
    //Attributen
    private final Collection<Kaart> startStapel;
    private final String naam;
    private int krediet;
    private final int geboorteJaar;

    //Constructor
    public Speler(String naam, int geboorteJaar, int krediet) {
        controleerGeboorteJaar(geboorteJaar);
        controleerNaam(naam);

        this.naam = naam;
        this.geboorteJaar = geboorteJaar;
        this.krediet = krediet;

        startStapel = new ArrayList<>();

        maakStartStapel();
    }

    //Methodes
    private void maakStartStapel() {
        Kaart[] kaartenArray = new Kaart[10];

        kaartenArray[0] = new Kaart(2, '+');
        kaartenArray[1] = new Kaart(4, '+');
        kaartenArray[2] = new Kaart(5, '+');
        kaartenArray[3] = new Kaart(6, '+');
        kaartenArray[4] = new Kaart(1, '-');
        kaartenArray[5] = new Kaart(2, '-');
        kaartenArray[6] = new Kaart(3, '-');
        kaartenArray[7] = new Kaart(5, '-');
        kaartenArray[8] = new Kaart(3, '*');
        kaartenArray[9] = new Kaart(1, '*');
        
        // + gekochte kaarten uit databank halen

        this.startStapel.addAll(Arrays.asList(kaartenArray));
    }
    @Override
    public String toString() {
        return String.format("%s, %d, %d", naam, geboorteJaar, krediet);
    }

    
    
    //Controle
    private void controleerNaam(String naam) {
        if (naam.length() < 3) {
            throw new IllegalArgumentException("Naam moet minstens 3 karakters lang zijn.");
        }
    }

    private void controleerGeboorteJaar(int geboorteJaar) {
        int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
        int leeftijd = huidigJaar - geboorteJaar;
        if (leeftijd > 99 || leeftijd < 6) {
            throw new IllegalArgumentException("Speler moet minstens 6 jaar of maximum 99 jaar oud worden dit jaar.");
        }
    }
    
    //Getters & Settesr
    public List<Kaart> geefStartStapel() {
        return (List)startStapel;
    }

    public String getNaam() {
        return naam;
    }

    public int getKrediet() {
        return krediet;
    }

    public void setKrediet(int krediet) {
        this.krediet = krediet;
    }

    public int getGeboorteJaar() {
        return geboorteJaar;
    }
}
