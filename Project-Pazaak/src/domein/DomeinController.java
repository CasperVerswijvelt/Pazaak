/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * ? Casper Verswijvelt 2016-2017
 */
package domein;

import domein.*;
import exceptions.PlayerAlreadyExistsException;
import java.util.List;

/**
 *
 * @author Casper
 */
public class DomeinController {
    //Attributen

    private Spel spel;
    private SpelerRespository spelerRepo;

    //Constructor
    public DomeinController() {
        spel = new Spel();
        spelerRepo= new SpelerRespository();
    }

    /**
     *
     * @param naam
     * @param geboorteDatum
     */
    public void maakNieuweSpelerAan(String naam, int geboorteDatum) throws PlayerAlreadyExistsException {
        spelerRepo.maakNieuweSpelerAan(naam, geboorteDatum);
    }
    
    public List<String> geefAlleSpelerNamen() {
        return spelerRepo.geefSpelerNamenLijst();
    }

    public void startPazaak() {
        // TODO - implement DomeinController.startPazaak
        throw new UnsupportedOperationException();
    }
    
    //Getters & Setters

    public Spel getSpel() {
        return spel;
    }

    public void setSpel(Spel spel) {
        this.spel = spel;
    }

    public SpelerRespository getSpelerRepo() {
        return spelerRepo;
    }

    public void setSpelerRepo(SpelerRespository spelerRepo) {
        this.spelerRepo = spelerRepo;
    }
    

}
