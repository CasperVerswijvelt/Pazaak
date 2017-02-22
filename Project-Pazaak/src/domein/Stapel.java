//TEST, GEEN ECHTE KLASSE


/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Casper
 */
public class Stapel {
    //Atributen
    private List<Kaart> kaarten;
    
    //Constructor
    public Stapel() {
        kaarten = new ArrayList<>();
        Kaart[] kaartenArray = new Kaart[10];
        
        kaartenArray[0] = new Kaart('+',2);
        kaartenArray[1] = new Kaart('+',4);
        kaartenArray[2] = new Kaart('+',5);
        kaartenArray[3] = new Kaart('+',6);
        kaartenArray[4] = new Kaart('-',1);
        kaartenArray[5] = new Kaart('-',2);
        kaartenArray[6] = new Kaart('-',3);
        kaartenArray[7] = new Kaart('-',5);
        kaartenArray[8] = new Kaart('*',3);
        kaartenArray[9] = new Kaart('*',1);
        
        this.kaarten.addAll(Arrays.asList(kaartenArray));
    }
    
    //Getters & Setters

    public List<Kaart> getKaarten() {
        return kaarten;
    }

    public void setKaarten(List<Kaart> kaarten) {
        this.kaarten = kaarten;
    }
    
}
