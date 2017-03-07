package domein;

import java.util.*;
import persistentie.SpelerMapper;

public class SpelerRespository {

    //Attributen
//    private Collection<Speler> spelers;
    private SpelerMapper sm;

    //Constructor
    public SpelerRespository() {
        sm = new SpelerMapper();
    }

    //Methodes
    public void voegToe(Speler speler) {
        if (!bestaat(speler.getNaam())) {
            sm.voegToe(speler);
        } else {
            throw new IllegalArgumentException("Naam is al in gebruik!");
        }
    }

    public boolean bestaat(String naam) {

//        for (Speler speler : sm.geefAlleSpelers()) {
//            return speler.getNaam().toLowerCase().equals(naam.toLowerCase());
//        }
//        return false;

        try{
            sm.geefSpeler(naam);
        }catch(RuntimeException e) {
            return false;
        }
        return true;
    }

    //Getters & Setterr
    public List<Speler> getSpelers() {
        return sm.geefAlleSpelers();
    }

    public void maakNieuweSpelerAan(String naam, int geboorteDatum) {
        Speler speler = new Speler(naam, geboorteDatum);
        this.voegToe(speler);
    }

}
