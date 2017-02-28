package domein;

import java.util.*;

public class Stapel {

    //Atributen
    private Collection<Kaart> kaarten;

    //Constructor
    public Stapel() {
        kaarten = new ArrayList<>();
        Kaart[] kaartenArray = new Kaart[10];

        kaartenArray[0] = new Kaart(2, '+');
        kaartenArray[1] = new Kaart(4, '+');
        kaartenArray[2] = new Kaart(5, '+');
        kaartenArray[3] = new Kaart(6, '+');
        kaartenArray[4] = new Kaart(1, '-');
        kaartenArray[5] = new Kaart(2, '-');
        kaartenArray[6] = new Kaart(3, '-');
        kaartenArray[7] = new Kaart(5, '-');
        kaartenArray[8] = new Kaart(3, '*');
        kaartenArray[9] = new Kaart(1, '*');

        this.kaarten.addAll(Arrays.asList(kaartenArray));
    }

    //Methodes
    @Override
    public String toString() {
        String res = "";
        for (Kaart kaart : kaarten) {
            res += kaart;
        }
        return res;
    }

    //Getters & Setters
    public Collection<Kaart> getKaarten() {
        return kaarten;
    }

    public void setKaarten(Collection<Kaart> kaarten) {
        this.kaarten = kaarten;
    }

}
