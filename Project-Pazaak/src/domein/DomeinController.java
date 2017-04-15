/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * ? Casper Verswijvelt 2016-2017
 */
package domein;

import domein.*;
import exceptions.PlayerAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casper
 */
public class DomeinController {
    //Attributen

    private Wedstrijd wedstrijd;
    private SpelerRespository spelerRepo;

    //Constructor
    public DomeinController() {
        spelerRepo= new SpelerRespository();
    }

    /**
     *
     * @param naam
     * @param geboorteDatum
     * @throws exceptions.PlayerAlreadyExistsException
     */
    public void maakNieuweSpelerAan(String naam, int geboorteDatum) throws PlayerAlreadyExistsException {
        spelerRepo.voegToe(new Speler(naam, geboorteDatum));
    }
    
    
    
    public boolean spelerBestaat(String naam) {
        return spelerRepo.bestaat(naam);
    }
    //AAN TE PASSEN//////////////////////////////////////////////////////////////////////// 
    public void maakNieuweWedstrijdAan(Speler speler1, Speler speler2) {
        this.wedstrijd = new Wedstrijd(speler1, speler2);
    }
    // AAN TE PASSEN///////////////////////////////////////////////////////////////////////
    public void slaKredietOp(Speler speler) {
        spelerRepo.slaKredietOp(speler);
    }
    // Metode selecteer speler
    ///////////////////////////////////////////////////////////////////////////////////////
    // Methode geefGeselecteerdeSpelers
    ///////////////////////////////////////////////////////////////////////////////////////
    // Methode geefSpelersZonderWedstrijdStapel
    ///////////////////////////////////////////////////////////////////////////////////////
    // Methode selecteerSpelerWedstrijdStapel
    ///////////////////////////////////////////////////////////////////////////////////////
    //Getters & Setters
    public List<String> geefAlleSpelerNamen() {
        return spelerRepo.geefSpelerNamenLijst();
    }
    private int geefAantalSpelers() {
        return spelerRepo.geefAantalSpelers();
    }
    public SpelerRespository getSpelerRepo() {
        return spelerRepo;
    }

    public void setSpelerRepo(SpelerRespository spelerRepo) {
        this.spelerRepo = spelerRepo;
    }
    public String[] geefSpelerInfo(String naam) {
        return spelerRepo.geefSpelerInfo(naam);
    }
    // AAN TE PASSEN PARAMETER////////////////////////////////////////////////////////////
    public String geefStartStapel(Speler speler) {
        List<Kaart> kaarten = spelerRepo.geefStartStapel(speler);
        String res = "";
        for(Kaart element : kaarten){
            res+=element.toString()+"\n";
        }
        return res;
    }
    
    public Wedstrijd geefWedstrijd() {
        return wedstrijd;
    }
    // Methode voegKaartToe //////////////////////////////////////////////////////////////
    // Methode selecteerKaart/////////////////////////////////////////////////////////////
    // Methode geefNietgeselecteerdeKaarten///////////////////////////////////////////////
    // Methode geefWedstrijdStapel////////////////////////////////////////////////////////
    // Methode veranderKrediet////////////////////////////////////////////////////////////
    // Methode maakNieuweSet//////////////////////////////////////////////////////////////
    // Methode geefSpelerAanBeurt/////////////////////////////////////////////////////////
    // VRAAG UC6 WAAROM IS HET NIET MOGELIJK OM JE SPELBORD TE BEVRIEZEN NA HET GEBRUIK VAN EEN SPECIALE KAART? MOET JE NIET TERUG NAAR STAP 6 IPV 8 IN UC

}
