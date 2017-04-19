package domein;

import exceptions.NoPlayersAvailableException;
import java.util.*;

/**
 * @author Casper
 */
public class DomeinController {

    //Attributen
    private final SpelerRepository spelerRepo;
    private Wedstrijd wedstrijd;
    private final List<Speler> geselecteerdeSpelers;
    private final List<Kaart> geselecteerdeKaarten;
    private Speler geselecteerdeSpelerWedstrijdstapel;

    //Constructor
    public DomeinController() {
        this.spelerRepo = new SpelerRepository();
        this.geselecteerdeSpelers = new ArrayList<>();
        this.geselecteerdeKaarten = new ArrayList<>();
    }
    
    public void maakNieuweSpelerAan(String naam, int geboorteJaar) {
        spelerRepo.maakNieuweSpelerAan(naam, geboorteJaar);
    }
    
    public boolean spelerBestaat(String naam) {
        return spelerRepo.bestaat(naam);
    }
    
    public String[] geefSpelerInfo(String naam) {
        return spelerRepo.geefSpelerInfo(naam);
    }
    
    public List<String> geefAlleSpelerNamen() {
        return spelerRepo.geefSpelersLijst();
    }

    /**
     *
     * @param naam
     */
    public void selecteerSpeler(String naam) {
        Speler speler = spelerRepo.geefSpeler(naam);
        
        this.geselecteerdeSpelers.add(speler);
        
        if (this.geselecteerdeSpelers.size() > 2) {
            this.geselecteerdeSpelers.remove(0);
        }
    }
    
    public List<String> geefGeselecteerdeSpelers() {
        List<String> lijst = new ArrayList<>();
        
        for (Speler element : geselecteerdeSpelers) {
            lijst.add(element.getNaam());
        }
        
        return lijst;
    }
    
    public void maakNieuweWedstrijd() {
        if (geselecteerdeSpelers.size() < 2) {
            throw new NoPlayersAvailableException();
        }
        this.wedstrijd = new Wedstrijd(geselecteerdeSpelers.get(0), geselecteerdeSpelers.get(1));
        
        geselecteerdeSpelers.clear();
    }
    
    public String[][] geefStartStapel() {
        
        return kaartenAlsString(geselecteerdeSpelerWedstrijdstapel.geefStartStapel());
    }
    public String[][] geefStartStapel(String naam) {
        return kaartenAlsString(spelerRepo.geefStartStapel(naam));
    }
    
    public int geefAantalSpelers() {
        return spelerRepo.geefSpelersLijst().size();
    }
    
    public List<String> geefSpelersZonderWedstrijdStapel() {

        List<Speler> spelers = wedstrijd.geefSpelersZonderWedstrijdStapel();
        List<String> res = new ArrayList<>();
        
        for (Speler element : spelers) {
            res.add(element.getNaam());
        }
        return res;
    }
    
    public void selecterSpelerWedstrijdStapel(String naam) {
        this.geselecteerdeSpelerWedstrijdstapel = wedstrijd.geefSpeler(naam);
    }
    
    //WERKT MOGELIJK NIET
    public String[][] geefNietGeselecteerdeKaarten() {
        List<Kaart> nietGeselecteerdeKaarten = geselecteerdeSpelerWedstrijdstapel.geefStartStapel();
        
        for (Kaart element : geselecteerdeKaarten) {
            nietGeselecteerdeKaarten.remove(element);
        }
        
        return kaartenAlsString(nietGeselecteerdeKaarten);
        
    }
    
    public void selecteerKaart(String[] kaart) {
        this.geselecteerdeKaarten.add(stringAlsKaart(kaart));
    }
    
    public void maakWedstrijdStapel() {
        wedstrijd.maakWedstrijdstapel(geselecteerdeKaarten, geselecteerdeSpelerWedstrijdstapel);
        geselecteerdeKaarten.clear();
        geselecteerdeSpelerWedstrijdstapel = null;
    }
    
    public void maakNieuweSet() {
        wedstrijd.maakNieuweSet();
    }
    
    public String geefSpelerAanBeurt() {
        return wedstrijd.geefSpelerAanBeurt();
    }
    
    public void veranderKrediet(String naam, int aantal) {
        Speler speler = wedstrijd.geefSpeler(naam);
        speler.setKrediet(speler.getKrediet() + aantal);
        spelerRepo.slaKredietOp(speler);
    }
    
    public void deelKaartUit() {
        wedstrijd.deelKaartUit();
    }
    
    public String[][] geefSpelBord() {
        return kaartenAlsString(wedstrijd.geefSpelBord());
    }
    
    public int geefScore() {
        return wedstrijd.geefScore();
    }
    
    public List<String> geefMogelijkeActies() {
        return wedstrijd.geefMogelijkeActies();
    }
    
    public void eindigBeurt() {
        wedstrijd.eindigBeurt();
    }
    
    public void bevriesBord() {
        wedstrijd.bevriesBord();
    }
    
    public String[][] geefWedstrijdStapel() {
        return kaartenAlsString(wedstrijd.geefWedstrijdStapel());
    }
    
    public void gebruikWedstrijdKaart(String[] kaart, char type) {
        wedstrijd.gebruikWedstrijdKaart(stringAlsKaart(kaart), type);
    }
    
    public boolean setIsKlaar() {
        return wedstrijd.setIsKlaar();
    }
    
    public String geefSetUitslag() {
        return wedstrijd.geefSetUitslag();
    }
    
    public String geefWinnaar() {
        return wedstrijd.geefWinnaar().getNaam();
    }
    public boolean wedstrijdIsKlaar() {
        return wedstrijd.isKlaar();
    }
    private String[][] kaartenAlsString(List<Kaart> kaarten) {
        String[][] res = new String[kaarten.size()][3];
        
        for (int i = 0; i < kaarten.size(); i++) {
            res[i][0] = kaarten.get(i).getType() + "";
            res[i][1] = kaarten.get(i).getWaarde() + "";
            res[i][2] = kaarten.get(i).getPrijs()+"";
        }
        return res;
    }
    
    public String[] geefWedstrijdSpelers() {
        List<Speler> spelers = wedstrijd.geefSpelers();
        String[] res = new String[spelers.size()];
        for(int i = 0; i<spelers.size();i++)
            res[i] = spelers.get(i).getNaam();
            
        return res;
    }
    public int[] geefWedstrijdTussenstand() {
        return wedstrijd.geefTussenstand();
    }
    public void registreerAantalWins(){
        wedstrijd.registreerAantalWins();
    }
    
    public String[][] geefNogNietGekochteKaarten(String naam){
        return kaartenAlsString(spelerRepo.geefNogNietGekochteKaarten(naam));
    }
    
    public String[][] geefAangekochteKaarten(String naam) {
        return kaartenAlsString(spelerRepo.geefAangekochteKaarten(naam));
    }
    
    public void koopKaart(String naam, String[] kaart){
        spelerRepo.koopKaart(naam, stringAlsKaart(kaart));
    }

    private Kaart stringAlsKaart(String[] kaart) {
        return new Kaart(Integer.parseInt(kaart[1]), kaart[0].charAt(0), Integer.parseInt(kaart[2]));
    }
    
}
