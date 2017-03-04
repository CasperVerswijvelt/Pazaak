package domein;

import java.util.*;

public class SpelerRespository {

    //Attributen
    private Collection<Speler> spelers;

    //Constructor
    public SpelerRespository() {
        this.spelers = new ArrayList<>();
    }

    //Methodes
    public void voegToe(Speler speler) {
        if (!bestaat(speler.getNaam())) {
            spelers.add(speler);
        } else {
            throw new IllegalArgumentException("Naam is al in gebruik!");
        }
    }
    
    public boolean bestaat(String naam) {
        if (spelers == null) {
            return false;
        } else {
            for (Speler speler : spelers) {
                return speler.getNaam().toLowerCase().equals(naam.toLowerCase());
            }
        }
        return false;
    }

    //Getters & Setterr
    public Collection<Speler> getSpelers() {
        return spelers;
    }
    
    public void setSpelers(Collection<Speler> spelers) {
        this.spelers = spelers;
    }

    /**
     *
     * @param naam
     * @param geboorteDatum
     */
    public void maakNieuweSpelerAan(String naam, int geboorteDatum) {
        Speler speler = new Speler(naam, geboorteDatum);
        this.voegToe(speler);
    }
    
}
