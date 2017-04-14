package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Set {
    //Attributen

    private List<Kaart> stapel;
    private int[] score;
    private List<List<Kaart>> spelbord;
    private int spelerAanBeurt;
    private boolean bevroren[];

    //Constructor
    public Set(Speler speler1, Speler speler2) {
        bevroren = new boolean[2];

        // 2 spelborden aanmaken voor de spelers
        spelbord = new ArrayList<>();
        spelbord.add(new ArrayList<Kaart>());
        spelbord.add(new ArrayList<Kaart>());

        //stapel aanmaken
        stapel = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            stapel.add(new Kaart(i, '*'));
            stapel.add(new Kaart(i, '+'));
            stapel.add(new Kaart(i, '-'));
        }
        //stapel shufflen
        Collections.shuffle(stapel);

        //Eerste speler aan beurt selecteren
        int gebJaarSpeler1 = speler1.getGeboorteDatum(), gebJaarSpeler2 = speler2.getGeboorteDatum();

        //Volgens geboortejaar
        if (gebJaarSpeler1 != gebJaarSpeler2) {
            spelerAanBeurt = gebJaarSpeler1 > gebJaarSpeler2 ? 0 : 1;
        } else {
            //volgens alfabetische volgorde
            String naamSpeler1 = speler1.getNaam().toLowerCase(), naamSpeler2 = speler2.getNaam().toLowerCase();
            int compare = naamSpeler1.compareTo(naamSpeler2);
            spelerAanBeurt = compare < 0 ? 0 : 1;
        }
    }

    //Methodes
    public void deelKaart(int speler) {
        controleerSpeler(speler);
        Kaart kaart = stapel.get(0);
        stapel.remove(0);
        spelbord.get(speler - 1).add(kaart);
        volgendeSpelerAanBeurt();
        
    }
    
    public void kiesKaart(int speler, Kaart kaart) {
        controleerSpeler(speler);
        spelbord.get(speler - 1).add(kaart);
        volgendeSpelerAanBeurt();
    }

    public void bevriesBord() {
        bevroren[spelerAanBeurt] = true;
        volgendeSpelerAanBeurt();
    }
    
    
    
    public int berekenScore(int speler) {
        controleerSpeler(speler);
        int res = 0;
        for (Kaart element : spelbord.get(speler - 1)) {
            res += element.getWaarde();
        }
        return res;
        
    }
    
    private void volgendeSpelerAanBeurt() {
        spelerAanBeurt = spelerAanBeurt == 0 ? 1 : 0;
    }

    //Getters & Setters
    public List<Kaart> getStapel() {
        return stapel;
    }
    
    public void setStapel(List<Kaart> stapel) {
        this.stapel = stapel;
    }
    
    public int getScore(int speler) {
        controleerSpeler(speler);
        return score[speler - 1];
    }
    
    public void setScore(int speler, int score) {
        controleerSpeler(speler);
        this.score[speler - 1] = score;
    }
    
    public List<Kaart> getSpelbord(int speler) {
        controleerSpeler(speler);
        return spelbord.get(speler - 1);
    }
    
    public void setSpelbord(int speler, List<Kaart> spelbord) {
        controleerSpeler(speler);
        this.spelbord.set(speler, spelbord);
    }
    
    public int getSpelerAanBeurt() {
        return spelerAanBeurt;
    }
    
    public void setSpelerAanBeurt(int spelerAanBeurt) {
        controleerSpeler(spelerAanBeurt);
        this.spelerAanBeurt = spelerAanBeurt;
    }

    //Controle
    public void controleerSpeler(int speler) {
        if (speler < 1 || speler > 2) {
            throw new IllegalArgumentException();
        }
    }
}
