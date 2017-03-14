package ui;


import domein.DomeinController;
import domein.Speler;
import persistentie.SpelerMapper;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Casper
 */
public class startUp {
    public static void main(String[] args) {
       // new Main().main();
       new Console(new DomeinController()).start();
//        
//        SpelerMapper sp = new SpelerMapper();
//        
//        Speler speler = new Speler("Matthias",1997);
//        speler.setKrediet(69);
//        sp.slaKredietOp(speler);
//                
//        for(Speler s: sp.geefAlleSpelers()){
//            System.out.println(s.toString());
//        }
//        Speler speler2 = new Speler("Casper",1998);
//        sp.voegToe(speler2);
//        Speler speler = sp.geefSpeler("Casper");
//        System.out.println(speler.getNaam());
//        Dit is een manier om de methodes van de mapper te testen.
    }
}
