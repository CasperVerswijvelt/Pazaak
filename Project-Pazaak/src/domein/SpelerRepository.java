package domein;

import exceptions.*;
import java.util.*;
import persistentie.KaartMapper;
import persistentie.SpelerMapper;

public class SpelerRepository {

    //Attributen
    private SpelerMapper sm;
    private KaartMapper km;

    //Constructor
    public SpelerRepository() {
        sm = new SpelerMapper();
        km = new KaartMapper();
    }

    //Methodes
    public void maakNieuweSpelerAan(String naam, int geboorteDatum) {
        Speler speler = new Speler(naam, geboorteDatum, 0, null);
        this.voegToe(speler);
    }

    public boolean bestaat(String naam) {
        return sm.geefSpeler(naam) != null;
    }

    public List<String> geefSpelersLijst() {
        return sm.geefAlleSpelerNamen();
    }

    public String[] geefSpelerInfo(String naam) {
        Speler speler = sm.geefSpeler(naam);
        String info[] = new String[3];

        info[0] = speler.getNaam();
        info[1] = speler.getKrediet() + "";
        info[2] = speler.getGeboorteJaar() + "";

        return info;
    }

    private void voegToe(Speler speler) {

        sm.voegToe(speler);

    }

    public Speler geefSpeler(String naam) {
        Speler speler = sm.geefSpeler(naam);
        speler.setStartStapel(km.geefStartStapel(naam));
        return speler;
    }

    public List<Kaart> geefStartStapel(String naam) {
        if (bestaat(naam)) {
            return km.geefStartStapel(naam);
        } else {
            throw new PlayerDoesntExistException();
        }
    }

    public void slaKredietOp(Speler speler) {
        sm.slaKredietOp(speler);
    }

    public List<Kaart> geefNogNietGekochteKaarten(String naam) {
        return km.geefNogNietGekochteKaarten(naam);
    }

    public void koopKaart(String naam, Kaart inputKaart) {
        Speler speler = geefSpeler(naam);
        int prijs = inputKaart.getPrijs();
        if (speler.getKrediet() >= prijs) {
            km.voegStartstapelKaartToe(naam, inputKaart);
            speler.setKrediet(speler.getKrediet() - prijs);
            slaKredietOp(speler);

        } else {
            throw new InsufficientBalanceException("Insufficient balance");
        }

    }

    public List<Kaart> geefAangekochteKaarten(String naam) {
        return km.geefAangekochteKaarten(naam);
    }

}
