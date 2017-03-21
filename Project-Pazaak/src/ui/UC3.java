/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package ui;

import domein.DomeinController;
import java.util.ResourceBundle;

/**
 *
 * @author Casper
 */
public class UC3 extends DomeinController{
    public void start(ResourceBundle r) {
        System.out.println(r.getString("CHOOSETWOPLAYERS"));
        toonBeschikbareSpelers();
    }

    private void toonBeschikbareSpelers() {
        
        for(String naam : super.geefAlleSpelerNamen()){
            System.out.println(naam);
        }
    }
}
