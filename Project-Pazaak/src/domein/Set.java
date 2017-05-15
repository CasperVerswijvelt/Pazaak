package domein;

import exceptions.*;
import java.util.*;

/**
 * Class with al methods used in a set
 * @author goran
 */
public class Set {

    //Attributen
    private List<Kaart> setStapel;
    private final List<Kaart> spelbord1;
    private final List<Kaart> spelbord2;
    private boolean speler1AanBeurt;
    private final boolean bevroren[];

    /**
     * Constructor that lets you create a set
     * @param eersteSpelerBegint
     */
    public Set(boolean eersteSpelerBegint) {
        bevroren = new boolean[2];
        speler1AanBeurt = eersteSpelerBegint;

        // 2 spelborden aanmaken voor de spelers
        spelbord1 = new ArrayList<>();
        spelbord2 = new ArrayList<>();

        //stapel aanmaken
        maakSetstapel();
    }

    private void maakSetstapel() {
        //Aanmaken
        setStapel = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 4; j++) {
                setStapel.add(new Kaart(i, '+'));
            }
        }
        //stapel shufflen
        Collections.shuffle(setStapel);
    }

    /**
     * method that returns an int that indicates which players turn it is
     * @return
     */
    public int geefSpelerAanBeurtIndex() {
        return speler1AanBeurt ? 0 : 1;
    }

    /**
     * method that draws a card
     */
    public void deelKaartUit() {
        if(setIsKlaar())
            return;
        
        Kaart kaart = setStapel.get(0);
        setStapel.remove(0);

        if (speler1AanBeurt) {
            if (!bevroren[0]) {
                spelbord1.add(kaart);
            }
        } else {
            if (!bevroren[1]) {
                spelbord2.add(kaart);
            }

        }
    }

    /**
     * method that return the gameboard
     * @return
     */
    public List<Kaart> geefSpelBord() {
        if (speler1AanBeurt) {
            return spelbord1;
        } else {
            return spelbord2;
        }
    }

    /**
     * method that return the score
     * @return
     */
    public int geefScore() {
        if (speler1AanBeurt) {
            return berekenScore(spelbord1);
        } else {
            return berekenScore(spelbord2);
        }
    }

    /**
     * method that checks if a gameboard is frozen or not for player at turn
     * @return
     */
    public boolean isBevroren() {
        if (bevroren[geefSpelerAanBeurtIndex()]) {
            return true;
        }
        return false;
    }

    /**
     * method that return the possible actions
     * @return
     */
    public List<String> geefMogelijkeActies() {
        List res = new ArrayList<>();
        if (!bevroren[geefSpelerAanBeurtIndex()] && !setIsKlaar()) {
            res.add("FREEZE");
            res.add("ENDTURN");
        }

        return res;
    }

    /**
     * method that ends the turn
     */
    public void eindigBeurt() {
        if (speler1AanBeurt) {
            if (!bevroren[1]) {
                speler1AanBeurt = false;
            }
        } else {
            if (!bevroren[0]) {
                speler1AanBeurt = true;
            }
        }
    }

    /**
     * method to freeze the gameboard for a player
     */
    public void bevriesBord() {
        bevroren[geefSpelerAanBeurtIndex()] = true;
    }

    /**
     * method to use a game card
     * @param kaart
     * @param Pwaarde
     * @param Ptype
     */
    public void gebruikWedstrijdKaart(Kaart kaart, int Pwaarde, char Ptype) {
        if(setIsKlaar())
            return;
        
        List<Kaart> huidigSpelbord = speler1AanBeurt?spelbord1:spelbord2;

        int waarde = kaart.getWaarde();
        char origineelType = kaart.getType();
        switch (origineelType) {
            case '+':
            case '-':
                controleerTypeGelijk(origineelType, Ptype);
                break;
            case '*':
                kaart.setType(Ptype);
                break;
            case 'T':
                controleerTypeGelijk(origineelType, Ptype);
                bevriesBord();
                break;
            case 'D':
                controleerTypeGelijk(origineelType, Ptype);
                Kaart teVerdubbelenKaart = huidigSpelbord.get(huidigSpelbord.size() - 1);
                teVerdubbelenKaart.setWaarde(teVerdubbelenKaart.getWaarde() * 2);
                break;
            case 'W':
                controleerTypeGelijk(origineelType, Ptype);
                int[] waardes;
                if (waarde == 1) {
                    waardes = new int[]{2, 4};
                } else if (waarde == 2) {
                    waardes = new int[]{3, 6};
                } else {
                    throw new InvalidCardException("Value " + waarde + " not allowed for this card");
                }
                for (Kaart element : huidigSpelbord) {
                    if ((element.getWaarde() == waardes[0] || element.getWaarde() == waardes[1]) && element.getType() != 'W') {
                        element.setType(element.getType() == '+' ? '-' : '+');
                    }
                }
                break;
            case 'C':
                kaart.setType(Ptype);
                kaart.setWaarde(Pwaarde);
                break;

        }
        char type = kaart.getType();
        if (type == 'C' || type == '*') {
            throw new InvalidCardException("Card 'C' or '*' must include a requested type and/or value");
        }
        huidigSpelbord.add(kaart);
    }

    /**
     * method that checks if a set has ended
     * @return
     */
    public boolean setIsKlaar() {
        return berekenScore(spelbord1) > 20 || berekenScore(spelbord2) > 20 || spelbord1.size() > 8 || spelbord2.size() > 8 || (bevroren[0] && bevroren[1]);

    }

    /**
     * method that returns the the index of the player who has won
     * @return
     */
    public int geefSetUitslagIndex() {
        if (setIsKlaar()) { // Set ten einde

            int score1 = berekenScore(spelbord1), score2 = berekenScore(spelbord2);
            if (score1 == score2) {
                if (spelbord1.size() > 8) {
                    return 0;
                }

                if (spelbord2.size() > 8) {
                    return 1;
                }
                
                //Kijken of er een speler een T kaart bevat
                boolean spelbord1BevatTKaart = spelbordBevatTKaart(spelbord1), spelbord2BevatTKaart = spelbordBevatTKaart(spelbord2);
                if (spelbord1BevatTKaart && !spelbord2BevatTKaart) {
                    return 0;
                } else if (spelbord2BevatTKaart && !spelbord1BevatTKaart) {
                    return 1;
                } else //Geen van beiden heeft T kaart  , gelijkspel
                {
                    return 2;
                }
            } else {
                if (score1 > 20) {
                    return 1;
                }
                if (score2 > 20) {
                    return 0;
                }

                if (spelbord1.size() > 8) {
                    return 0;
                }

                if (spelbord2.size() > 8) {
                    return 0;
                }

                if (score1 > score2) {
                    return 0;
                } else { //score1<score2
                    return 1;
                }
            }

        } else // Set nog niet ten einde
        {
            return -1;
        }
    }

    private int berekenScore(List<Kaart> kaarten) {
        int score = 0;
        for (Kaart element : kaarten) {
            int waarde = element.getWaarde();
            switch (element.getType()) {
                case '+':
                    score += waarde;
                    break;
                case '-':
                    score -= waarde;
                    break;
                case 'T':
                    score += waarde;
                    break;
                case 'D':
                    break;
                case 'W':
                    break;
                case 'C':
                case '*':
                    throw new InvalidCardException("Invalid card on board");
            }

        }
        return score;
    }

    private void controleerTypeGelijk(char type1, char type2) {
        if (type1 != type2) {
            throw new InvalidCardException("Original cardtype and requested type doesn't match, stop cheating!");
        }
    }

    private boolean spelbordBevatTKaart(List<Kaart> spelbord) {
        Kaart TKaart = new Kaart(1, 'T', 20);
        for (Kaart element : spelbord) {
            if (element.equals(TKaart)) {
                return true;
            }
        }
        return false;
    }
}
