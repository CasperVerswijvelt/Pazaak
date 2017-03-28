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
    public void voegToe(Speler speler) throws PlayerAlreadyExistsException {
        if (!bestaat(speler.getNaam())) {
            sm.voegToe(speler);
        } else {
            throw new PlayerAlreadyExistsException("Naam is al in gebruik!");
        }
    }

    public boolean bestaat(String naam) {
        return sm.geefSpeler(naam) != null;
    }

    public void maakNieuweSpelerAan(String naam, int geboorteDatum) throws PlayerAlreadyExistsException {
        Speler speler = new Speler(naam, geboorteDatum);
        this.voegToe(speler);
    }

    public List<String> geefSpelerNamenLijst() {
        return sm.geefAlleSpelerNamen();
    }

    public int geefAantalSpelers() {
        return sm.geefAlleSpelers().size();
    }

    public String[] geefSpelerInfo(String naam) {
        Speler speler = sm.geefSpeler(naam);
        String info[] = new String[4];

        info[0] = speler.getNaam();
        info[1] = speler.getKrediet() + "";
        info[2] = speler.getGeboorteDatum() + "";

        String kaarten = "";
        for (Kaart element : speler.getStartStapel()) {
            kaarten += element.toString() + ", ";
        }
        info[3] = kaarten;

        return info;
    }

    public Speler geefSpeler(String naam) {
        return sm.geefSpeler(naam);
    }
    public List<Kaart> geefStartStapel(Speler speler) {
        return speler.getStartStapel();
    }

    //Getters & Setterr
    public List<Speler> getSpelers() {
        return sm.geefAlleSpelers();
    }

}
