package domein;

import java.util.*;
import persistentie.SpelerMapper;
import exceptions.PlayerAlreadyExistsException;

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

    private boolean bestaat(String naam) {
        return sm.geefSpeler(naam)!=null;
    }

    //Getters & Setterr
    public List<Speler> getSpelers() {
        return sm.geefAlleSpelers();
    }

    public void maakNieuweSpelerAan(String naam, int geboorteDatum) throws PlayerAlreadyExistsException {
        Speler speler = new Speler(naam, geboorteDatum);
        this.voegToe(speler);
    }

    public List<String> geefSpelerNamenLijst() {
        return sm.geefAlleSpelerNamen();
    }

}
