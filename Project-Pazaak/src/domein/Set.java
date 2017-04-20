package domein;

import exceptions.InvalidCardException;
import java.util.*;

public class Set {

    //Attributen
    private List<Kaart> setStapel;
    private final List<Kaart> spelbord1;
    private final List<Kaart> spelbord2;
    private boolean speler1AanBeurt;
    private final boolean bevroren[];

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
    public boolean isBevroren() {
        if(bevroren[geefSpelerAanBeurtIndex()])
            return true;
        return false;
    }

    public List<String> geefMogelijkeActies() {
        List res = new ArrayList<>();
        if(!bevroren[geefSpelerAanBeurtIndex()] && !setIsKlaar()){
            res.add("FREEZE");
            res.add("ENDTURN");
        }

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

    public void gebruikWedstrijdKaart(Kaart kaart, int Pwaarde ,char Ptype) {
        List<Kaart> huidigSpelbord;
        if (speler1AanBeurt) {        
            huidigSpelbord = spelbord1;
        } else {
            huidigSpelbord = spelbord2;
        }
        
        int waarde = kaart.getWaarde();
        char origineelType = kaart.getType();
        switch(origineelType){
            case '+':case '-':
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
                Kaart teVerdubbelenKaart = huidigSpelbord.get(huidigSpelbord.size()-2);
                teVerdubbelenKaart.setWaarde(teVerdubbelenKaart.getWaarde());
                break;
            case 'W': 
                controleerTypeGelijk(origineelType, Ptype);
                int[] waardes;
                if(waarde == 1)
                    waardes =  new int[]{2,4};
                else if(waarde == 2)
                    waardes = new int[]{3,6};
                else
                    throw new InvalidCardException("Value "+waarde+" not allowed for this card");
                for(Kaart element : huidigSpelbord) {
                    if(element.getWaarde() == waardes[0] || element.getWaarde() == waardes[1]) {
                        element.setType(element.getType()=='+'?'-':'+');
                    }
                }
                break;
            case 'C':
                kaart.setType(Ptype);
                kaart.setWaarde(waarde);
                    
        }
        huidigSpelbord.add(kaart);
    }

    public boolean setIsKlaar() {
        return berekenScore(spelbord1) > 20 || berekenScore(spelbord2) > 20 || spelbord1.size() > 8 || spelbord2.size() > 8 || (bevroren[0] && bevroren[1]);

    }

    public int geefSetUitslagIndex() {
        if(setIsKlaar()) { // Set ten einde
            
            //Kijken of er een speler 9 kaarten heeft behaald
            if(spelbord1.size() > 8)
                return 0;
            else if(spelbord2.size() > 8)
                return 1;
            
            //geen 9 kaarten behaald, er wordt naar score gekeken voor uitslag
            int score1 = berekenScore(spelbord1), score2 = berekenScore(spelbord2);
            if(score1==score2) 
                return 2;
            else {
                if(score1>20)
                    return 1;
                if(score2>20)
                    return 0;
                if(score1>score2)
                    return 0;
                else
                    return 1;
            }
                  
        } else // Set nog niet ten einde
            return -1;
    }

    private int berekenScore(List<Kaart> kaarten) {
        int score = 0;
        for (Kaart element : kaarten) {
            int waarde = element.getWaarde();
            switch(element.getType()) {
                case '+':
                    score += waarde;
                    break;
                case '-':
                    score -= waarde;
                    break;
                case 'T':
                    score+=waarde;
                    break;
                case 'D': 
                    break;
                case 'W':
                    break;
                case 'C':case '*':
                    throw new IllegalArgumentException("Invalid card on board");
            }
            
        }
        return score;
    }
    
    
    private void controleerTypeGelijk(char type1, char type2) {
        if( type1!=type2)
            throw new InvalidCardException("Original cardtype and requested type doesn't match, stop cheating!");
    }
}
