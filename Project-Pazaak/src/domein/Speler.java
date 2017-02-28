package domein;

import java.util.Calendar;

public class Speler {
    //Attributen
    private String naam;
    private int krediet;
    private int geboorteDatum;
    private Stapel stapel;
    private int taal;

    /**
     *
     * @param naam
     * @param geboorteDatum
     */

    

    

    //Constructors
    //Speler uit DB
    public Speler(String naam, int geboorteJaar, int krediet, Stapel stapel) {
        controleerGeboorteJaar(geboorteJaar);
        controleerNaam(naam);

        this.stapel = stapel;
        this.naam = naam;
        this.geboorteDatum = geboorteJaar;
        this.krediet = krediet;
    }
    //Nieuwe speler
    public Speler(String naam, int geboorteDatum) {
        this(naam, geboorteDatum, 0, new Stapel());
    }

    //Methodes
    
    public void kiesTaal() {
        // TODO - implement Speler.kiesTaal
        throw new UnsupportedOperationException();
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

    public Stapel getStapel() {
        return stapel;
    }

    public void setStapel(Stapel stapel) {
        this.stapel = stapel;
    }

    

}
