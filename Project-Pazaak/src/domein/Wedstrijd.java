/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package domein;

import exceptions.CardStackTooBigException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casper
 */
public class Wedstrijd {

    //Attributen
    private final List<Speler> spelers;
    private List< List<Kaart>> wedstrijdStapels;
    private int[] aantalWins;
    private Set huidigeSet;

    //Constructor
    public Wedstrijd(Speler speler1, Speler speler2) {

        aantalWins = new int[2];
        this.wedstrijdStapels = new ArrayList<>();
        wedstrijdStapels.add(null);
        wedstrijdStapels.add(null);
        this.spelers = new ArrayList<>();

        this.spelers.add(0, speler1);
        this.spelers.add(1, speler2);

    }

    //Controle
    private void controleerWedstrijdStapel(List<Kaart> stapel) {
        if (stapel.size() > 4) {
            throw new CardStackTooBigException();
        }
    }

    //Methodes
    public void voegKaartToe(int speler, Kaart kaart) {
        wedstrijdStapels.get(speler).add(kaart);
    }

    public boolean heeftWinnaar() {
        for (int element : aantalWins) {
            if (element > 2) {
                return true;
            }
        }
        return false;
    }

    public void startSet() {
        this.huidigeSet = new Set(spelers.get(0), spelers.get(1));

    }

    public Speler getSpelerAanBeurt() {
        return spelers.get(huidigeSet.getSpelerAanBeurt());
    }

    //Getters & Setters
    public List<Kaart> getWedstrijdStapels(int index) {
        return wedstrijdStapels.get(index);
    }

    public void setWedstrijdStapel(int index, List<Kaart> stapel) {
        controleerWedstrijdStapel(stapel);
        this.wedstrijdStapels.set(index, stapel);
    }

    public List<Speler> getSpelers() {
        return spelers;
    }

    public Speler geefWinnaar() {
        if (aantalWins[0] > 2) {
            return spelers.get(0);
        } else if (aantalWins[1] > 2) {
            return spelers.get(1);
        } else {
            return null;
        }
    }

}
