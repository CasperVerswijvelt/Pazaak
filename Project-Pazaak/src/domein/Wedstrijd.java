package domein;

import exceptions.CardStackTooBigException;
import java.util.*;

public class Wedstrijd {

    private List<Speler> spelers;
    private Set huidigeSet;
    private List<List<Kaart>> wedstrijdStapels;
    private int[] aantalGewonnen;

    /**
     *
     * @param speler1
     * @param speler2
     */
    public Wedstrijd(Speler speler1, Speler speler2) {
        aantalGewonnen = new int[2];
        this.wedstrijdStapels = new ArrayList<>();
        wedstrijdStapels.add(null);
        wedstrijdStapels.add(null);
        this.spelers = new ArrayList<>();

        int gebJaarSpeler1 = speler1.getGeboorteJaar(), gebJaarSpeler2 = speler2.getGeboorteJaar();

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

    //Methodes
    public List<Speler> geefSpelersZonderWedstrijdStapel() {
        List list = new ArrayList<Speler>();

        for (int i = 0; i < wedstrijdStapels.size(); i++) {
            if (wedstrijdStapels.get(i) == null) {
                list.add(spelers.get(i));
            }
        }
        return list;
    }

    public void maakWedstrijdstapel(List<Kaart> kaarten, Speler speler) {
        while (kaarten.size() > 4) {
            kaarten.remove(kaarten.get((int) (Math.random() * kaarten.size())));
        }
        List<Kaart> kaartenKopie = new ArrayList<>(kaarten);
        this.wedstrijdStapels.set(spelers.indexOf(speler), kaartenKopie);
    }

    public void maakNieuweSet() {
        this.huidigeSet = new Set(spelers.get(0), spelers.get(1));
    }

    public String geefSpelerAanBeurt() {
        return spelers.get(huidigeSet.geefSpelerAanBeurtIndex()).getNaam();
    }

    public void deelKaartUit() {
        huidigeSet.deelKaartUit();
    }

    public List<Kaart> geefSpelBord() {
        return huidigeSet.geefSpelBord();
    }

    public int geefScore() {
        return huidigeSet.geefScore();
    }

    public List<String> geefMogelijkeActies() {
        List res = huidigeSet.geefMogelijkeActies();
        if (!wedstrijdStapels.get(huidigeSet.geefSpelerAanBeurtIndex()).isEmpty() && !res.isEmpty()) {
            res.add("USEGAMECARD");
        }
        return res;
    }

    public void eindigBeurt() {
        huidigeSet.eindigBeurt();
    }

    public void bevriesBord() {
        huidigeSet.bevriesBord();
    }

    public Speler geefSpeler(String naam) {
        for (Speler element : spelers) {
            if (element.getNaam().toLowerCase().equals(naam.toLowerCase())) {
                return element;
            }
        }
        return null;
    }

    public List<Kaart> geefWedstrijdStapel() {
        return wedstrijdStapels.get(huidigeSet.geefSpelerAanBeurtIndex());
    }

    public void gebruikWedstrijdKaart(Kaart kaart, char type) {
        List<Kaart> wedstrijdStapel = geefWedstrijdStapel();
        for(Kaart element : wedstrijdStapel) {
            if(element.equals(kaart)) {
                wedstrijdStapel.remove(element);
                break;
            }
                
        }

        huidigeSet.gebruikWedstrijdKaart(kaart, type);
    }

    public boolean setIsKlaar() {
        return huidigeSet.setIsKlaar();
    }

    public String geefSetUitslag() {
        int uitslag =  huidigeSet.geefSetUitslagIndex();
        
        switch(uitslag) {
            case 0 : case 1:
                return spelers.get(uitslag).getNaam();
            case 2 :
                return "TIE";
            default: 
                return null;
        }
    }

    public Speler geefWinnaar() {
        if (isKlaar()) {
            int indexGewonnen = -1;
            for (int i = 0; i < 2; i++) {
                if (aantalGewonnen[i] > 2) {
                    indexGewonnen = i;
                }
            }
            return spelers.get(indexGewonnen);
        } else {
            return null;
        }
    }

    public boolean isKlaar() {
        for (int i = 0; i < 2; i++) {
            if (aantalGewonnen[i] > 2) {
                return true;
            }
        }
        return false;
    }

    //Getters & Setters
    public List<Speler> geefSpelers() {
        return spelers;
    }
    
    public int[] geefTussenstand() {
        return aantalGewonnen;
    }

    public void registreerAantalWins() {
        int uitslag = huidigeSet.geefSetUitslagIndex();
        if(uitslag == 0 || uitslag ==1) {
            aantalGewonnen[uitslag] ++;
        }
    }
}
