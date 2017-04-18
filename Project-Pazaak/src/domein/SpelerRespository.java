package domein;

import exceptions.PlayerAlreadyExistsException;
import exceptions.PlayerDoesntExistException;
import java.util.*;
import persistentie.SpelerMapper;

public class SpelerRespository {
    //Attributen
    private SpelerMapper sm;
    
    //Constructor
    public SpelerRespository() {
        sm = new SpelerMapper();
    }
    
    //Methodes
    public void maakNieuweSpelerAan(String naam, int geboorteDatum) {
        Speler speler = new Speler(naam, geboorteDatum, 0);
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
        String info[] = new String[4];

        info[0] = speler.getNaam();
        info[1] = speler.getKrediet() + "";
        info[2] = speler.getGeboorteJaar() + "";

        return info;
    }
    

    private void voegToe(Speler speler) {
        if (!bestaat(speler.getNaam())) {
            sm.voegToe(speler);
        } else {
            throw new PlayerAlreadyExistsException("Naam is al in gebruik!");
        }
    }

    public Speler geefSpeler(String naam) {
        return sm.geefSpeler(naam);
    }

    public List<Kaart> geefStartStapel(String naam) {
        if(bestaat(naam))
            return sm.geefSpeler(naam).geefStartStapel();
        else
            throw new PlayerDoesntExistException();
    }

    public void slaKredietOp(Speler speler) {
        sm.slaKredietOp(speler);
    }
    
    public List<Kaart> geefAankoopbareKaarten(String naam){
        List<Kaart> alleAankoopbareKaarten = sm.geefAlleAankoopbareKaarten();
        List<Kaart> startStapel = geefStartStapel(naam);
        int startStapelGrootte = startStapel.size();
        if(startStapelGrootte == 10)
            return alleAankoopbareKaarten;
        List<Kaart> aangekochteKaarten = new ArrayList<>();
        for(int i = 9; i<startStapelGrootte;i++) {
            aangekochteKaarten.add(startStapel.get(i));
        }
        int alleAankoopbareKaartenGrootte = alleAankoopbareKaarten.size();
        int alleAangekochteKaartenGroote = aangekochteKaarten.size();
        for(int i = 0; i<alleAankoopbareKaartenGrootte; i++) {
            for(int j = 0; j< alleAangekochteKaartenGroote; j++){
                if(alleAankoopbareKaarten.get(i).equals(aangekochteKaarten.get(j))) {
                    alleAankoopbareKaarten.remove(i);
                    i--;
                    alleAankoopbareKaartenGrootte--;
                    aangekochteKaarten.remove(j);
                    j--;
                    alleAangekochteKaartenGroote--;
                }
            }
        }
        return alleAankoopbareKaarten;
    }

}
