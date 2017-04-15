package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Speler {

    //Attributen
    private String naam;
    private int krediet;
    private int geboorteDatum;
    private List<Kaart> startStapel;

    /**
     *
     * @param naam
     * @param geboorteDatum
     */
    //Constructors
    //Speler uit DB
    public Speler(String naam, int geboorteJaar, int krediet) {
        controleerGeboorteJaar(geboorteJaar);
        controleerNaam(naam);

        this.naam = naam;
        this.geboorteDatum = geboorteJaar;
        this.krediet = krediet;

        startStapel = new ArrayList<>();

        maakStartStapel();

    }
    //Nieuwe speler

    public Speler(String naam, int geboorteDatum) {
        this(naam, geboorteDatum, 0);
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

    //Methodes
    @Override
    public String toString() {
        return String.format("%s, %d, %d", getNaam(), getGeboorteDatum(), getKrediet());
    }

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

        this.startStapel.addAll(Arrays.asList(kaartenArray));
    }

    //Getters & Setters
    public int getKrediet() {
        return krediet;
    }

    public void setKrediet(int krediet) {
        this.krediet = krediet;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        controleerNaam(naam);
        this.naam = naam;
    }

    public int getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteJaar(int geboorteJaar) {
        controleerGeboorteJaar(geboorteJaar);
        this.geboorteDatum = geboorteJaar;
    }

    public List<Kaart> getStartStapel() {
        return startStapel;
        // + gekochte kaarten uit databank halen
    }

    public void setStartStapel(List<Kaart> kaarten) {
        this.startStapel = kaarten;
    }

}
