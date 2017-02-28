/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package ui;

import domein.DomeinController;

/**
 *
 * @author Casper
 */
public class Main {
    public static void main(String[] args) {
        DomeinController dc = new DomeinController();
        
        dc.maakNieuweSpelerAan("jan", 1998);
        System.out.println(dc.getSpelerRepo().getSpelers().size());
    }
}
