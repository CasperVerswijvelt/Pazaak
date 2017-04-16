package domein;

import java.util.*;

public class Set {

    //Attributen
    private List<Kaart> setStapel;
    private final List<Speler> spelers;
    private final List<Kaart> spelbord1;
    private final List<Kaart> spelbord2;
    private boolean speler1AanBeurt;
    private final boolean bevroren[];

    public Set(Speler speler1, Speler speler2) {
        bevroren = new boolean[2];
        speler1AanBeurt = true;

        // 2 spelborden aanmaken voor de spelers
        spelbord1 = new ArrayList<>();
        spelbord2 = new ArrayList<>();

        //stapel aanmaken
        maakSetstapel();

        spelers = new ArrayList<>();
        spelers.add(speler1);
        spelers.add(speler2);
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

    public String geefSpelerAanBeurt() {
        return speler1AanBeurt ? spelers.get(0).getNaam() : spelers.get(1).getNaam();
    }

    public int geefSpelerAanBeurtIndex() {
        return speler1AanBeurt ? 0 : 1;
    }

    public void deelKaartUit() {
        Kaart kaart = setStapel.get(0);
        setStapel.remove(0);

        if (speler1AanBeurt) {
            spelbord1.add(kaart);
        } else {
            spelbord2.add(kaart);
        }
    }

    public List<Kaart> geefSpelBord() {
        if (speler1AanBeurt) {
            return spelbord1;
        } else {
            return spelbord2;
        }
    }

    public int geefScore() {
        if (speler1AanBeurt) {
            return berekenScore(spelbord1);
        } else {
            return berekenScore(spelbord2);
        }
    }

    public List<String> geefMogelijkeActies() {
        List res = new ArrayList<>();
        res.add("FREEZE");
        res.add("ENDTURN");

        return res;
    }

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

    public void bevriesBord() {
        bevroren[geefSpelerAanBeurtIndex()] = true;
    }

    public void gebruikWedstrijdKaart(Kaart kaart, char type) {
        kaart.setType(type);
        if (speler1AanBeurt) {
            spelbord1.add(kaart);
        } else {
            spelbord2.add(kaart);
        }
    }

    public boolean setIsKlaar() {
        boolean bool = false;
        if (berekenScore(spelbord1) > 20 || berekenScore(spelbord2) > 20 || spelbord1.size() > 8 || spelbord2.size() > 8 || (bevroren[0] && bevroren[1]))
            bool = true;

        return bool;

    }

    public String geefSetUitslag() {
        if(setIsKlaar()) { // Set ten einde
            
            //Kijken of er een speler 9 kaarten heeft behaald
            if(spelbord1.size() > 8)
                return spelers.get(0).getNaam();
            else if(spelbord2.size() > 8)
                return spelers.get(1).getNaam();
            
            //geen 9 kaarten behaald, er wordt naar score gekeken voor uitslag
            int score1 = berekenScore(spelbord1), score2 = berekenScore(spelbord2);
            if(score1==score2) 
                return "TIE";
            else if(score1>score2 && score1<21)
                return spelers.get(0).getNaam();
            else 
                return spelers.get(1).getNaam();
        } else // Set nog niet ten einde
            return null;
    }

    private int berekenScore(List<Kaart> kaarten) {
        int score = 0;
        for (Kaart element : kaarten) {
            if (element.getType() == '+') {
                score += element.getWaarde();
            } else {
                score -= element.getWaarde();
            }
        }
        return score;
    }
}
