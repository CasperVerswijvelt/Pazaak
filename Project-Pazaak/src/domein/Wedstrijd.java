package domein;

import exceptions.*;
import java.util.*;

/**
 *
 * @author Casper
 */
public class Wedstrijd {

    private List<Speler> spelers;
    private Set huidigeSet;
    private List<List<Kaart>> wedstrijdStapels;
    private int[] aantalGewonnen;
    private boolean eersteSpelerBegint;

    /**
     *Initializes a new game with give players
     * @param speler1
     * @param speler2
     */
    //Nieuwe wedstrijd
    public Wedstrijd(Speler speler1, Speler speler2) {
        this.aantalGewonnen = new int[2];
        this.wedstrijdStapels = new ArrayList<>();
        this.wedstrijdStapels.add(null);
        this.wedstrijdStapels.add(null);
        this.spelers = new ArrayList<>();
        this.eersteSpelerBegint = true;

        int gebJaarSpeler1 = speler1.getGeboorteJaar(), gebJaarSpeler2 = speler2.getGeboorteJaar();
        
        if(speler1.getNaam().equalsIgnoreCase(speler2.getNaam()))
            throw new PlayersInGameAreSameException();

        //Speler volgens geboortejaar in goede volgorde opslaan
        if (gebJaarSpeler1 != gebJaarSpeler2) {
            if (gebJaarSpeler1 > gebJaarSpeler2) {
                spelers.add(speler2);
                spelers.add(speler1);
            } else {
                spelers.add(speler1);
                spelers.add(speler2);
            }
        } else {
            //volgens alfabetische volgorde
            String naamSpeler1 = speler1.getNaam().toLowerCase(), naamSpeler2 = speler2.getNaam().toLowerCase();
            int compare = naamSpeler1.compareTo(naamSpeler2);
            if (compare < 0) {
                spelers.add(speler1);
                spelers.add(speler2);
            } else {
                spelers.add(speler2);
                spelers.add(speler1);
            }

        }
    }

    //Wedstrijd uit DB

    /**
     *Initializes a game that was aved in the database, with given information
     * @param speler1
     * @param speler2
     * @param wedstrijdStapel1
     * @param wedstrijdStapel2
     * @param beginnendeSpeler
     * @param score1
     * @param score2
     */
    public Wedstrijd(Speler speler1, Speler speler2, List<Kaart> wedstrijdStapel1, List<Kaart> wedstrijdStapel2, String beginnendeSpeler, int score1, int score2) {
        this.aantalGewonnen = new int[2];
        this.wedstrijdStapels = new ArrayList<>();

        this.spelers = new ArrayList<>();
        this.eersteSpelerBegint = true;

        this.spelers.add(speler1);
        this.spelers.add(speler2);
        this.wedstrijdStapels.add(wedstrijdStapel1);
        this.wedstrijdStapels.add(wedstrijdStapel2);
        aantalGewonnen[0] = score1;
        aantalGewonnen[1] = score2;

        if (spelers.get(0).getNaam().equalsIgnoreCase(beginnendeSpeler)) {
            eersteSpelerBegint = true;
        } else {
            eersteSpelerBegint = false;
        }

    }

    //Methodes

    /**
     *Returns the players that have not yet created a game deck
     * @return
     */
    public List<Speler> geefSpelersZonderWedstrijdStapel() {
        List list = new ArrayList<Speler>();

        for (int i = 0; i < wedstrijdStapels.size(); i++) {
            if (wedstrijdStapels.get(i) == null) {
                list.add(spelers.get(i));
            }
        }
        return list;
    }

    /**
     *Sets the given list of cards for the given player in the game
     * @param kaarten
     * @param speler
     */
    public void maakWedstrijdstapel(List<Kaart> kaarten, Speler speler) {
        while (kaarten.size() > 4) {
            kaarten.remove(kaarten.get((int) (Math.random() * kaarten.size())));
        }
        List<Kaart> kaartenKopie = new ArrayList<>(kaarten);
        this.wedstrijdStapels.set(spelers.indexOf(speler), kaartenKopie);
    }

    /**
     *Starts a new set for the game
     */
    public void maakNieuweSet() {
        this.huidigeSet = new Set(eersteSpelerBegint);
        veranderBeginSpeler();
    }

    /**
     *Returns the player at turn
     * @return
     */
    public String geefSpelerAanBeurt() {
        return spelers.get(huidigeSet.geefSpelerAanBeurtIndex()).getNaam();
    }

    /**
     *Gives out a card from the set deck to the player at turn
     */
    public void deelKaartUit() {
        huidigeSet.deelKaartUit();
    }

    /**
     *Returns the game board of the player currently at turn
     * @return
     */
    public List<Kaart> geefSpelBord() {
        return huidigeSet.geefSpelBord();
    }

    /**
     * Returns the score of the player currently at turn
     * @return
     */
    public int geefScore() {
        return huidigeSet.geefScore();
    }

    /**
     *Gives the playing possibilities for the player at turn
     * @return
     */
    public List<String> geefMogelijkeActies() {
        List res = huidigeSet.geefMogelijkeActies();
        if (!wedstrijdStapels.get(huidigeSet.geefSpelerAanBeurtIndex()).isEmpty() && !res.isEmpty()) {
            res.add("USEGAMECARD");
        }
        return res;
    }

    /**
     *End turn for player currently at turn
     */
    public void eindigBeurt() {
        huidigeSet.eindigBeurt();
    }

    /**
     *Freezes the gameboard of the player at turn
     */
    public void bevriesBord() {
        huidigeSet.bevriesBord();
    }

    /**
     *Returns the player object of the given playername, if it is participating in the game
     * @param naam
     * @return
     */
    public Speler geefSpeler(String naam) {
        for (Speler element : spelers) {
            if (element.getNaam().toLowerCase().equals(naam.toLowerCase())) {
                return element;
            }
        }
        return null;
    }

    /**
     *Returns the game deck of the player at turn in the game
     * @return
     */
    public List<Kaart> geefWedstrijdStapel() {
        return wedstrijdStapels.get(huidigeSet.geefSpelerAanBeurtIndex());
    }

    /**
     *Returns the game deck for the player at given index
     * @param index
     * @return
     */
    public List<Kaart> geefWedstrijdStapel(int index) {
        return wedstrijdStapels.get(index);
    }

    /**
     *uses given gamecard from side deck, if it is on your sidedeck
     * @param kaart
     * @param gewensteWaarde
     * @param gewenstType
     */
    public void gebruikWedstrijdKaart(Kaart kaart, int gewensteWaarde, char gewenstType) {
        List<Kaart> wedstrijdStapel = geefWedstrijdStapel();

        for (Kaart element : wedstrijdStapel) {
            if (element.equals(kaart)) {
                wedstrijdStapel.remove(element);
                huidigeSet.gebruikWedstrijdKaart(kaart, gewensteWaarde, gewenstType);
                return;
            }

        }
        throw new CardNotInGameDeckException();

    }

    /**
     *returns if the set is over
     * @return
     */
    public boolean setIsKlaar() {
        return huidigeSet.setIsKlaar();
    }

    /**
     *Returns the outcome of the set, a playername or TIE, or null if set is not done
     * @return
     */
    public String geefSetUitslag() {
        int uitslag = huidigeSet.geefSetUitslagIndex();

        switch (uitslag) {
            case 0:
            case 1:
                return spelers.get(uitslag).getNaam();
            case 2:
                return "TIE";
            default:
                return null;
        }
    }

    /**
     *Returns the winner of the game
     * @return
     */
    public Speler geefWinnaar() {
        if (isKlaar()) {
            int indexGewonnen = -1;
            for (int i = 0; i < 2; i++) {
                if (aantalGewonnen[i] > 2) {
                    indexGewonnen = i;
                }
            }
            return spelers.get(indexGewonnen);
        }
        throw new NoWinnerException();
    }

    /**
     *Returns if the game is over
     * @return
     */
    public boolean isKlaar() {
        for (int i = 0; i < 2; i++) {
            if (aantalGewonnen[i] > 2) {
                return true;
            }
        }
        return false;
    }

    private void veranderBeginSpeler() {
        this.eersteSpelerBegint = !this.eersteSpelerBegint;
    }

    //Getters & Setters

    /**
     *Returns players participating in the game
     * @return
     */
    public List<Speler> geefSpelers() {
        return spelers;
    }

    /**
     *Returns the amount of wins for each player in the game
     * @return
     */
    public int[] geefTussenstand() {
        return aantalGewonnen;
    }

    /**
     *Register a win for the winning setplayer, if there is a winner
     */
    public void registreerAantalWins() {
        int uitslag = huidigeSet.geefSetUitslagIndex();
        if (uitslag == 0 || uitslag == 1) {
            aantalGewonnen[uitslag]++;
        }
    }
}
