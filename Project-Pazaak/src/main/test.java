/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.Kaart;
import domein.SpelerRespository;
import persistentie.KaartMapper;
import persistentie.SpelerMapper;

/**
 *
 * @author Casper
 */
public class test {
    
    
    
    public static void main(String[] args) {
//        new KaartMapper().bouwKaartTypeDatabase();
        SpelerRespository spelerRepo = new SpelerRespository();
        String naam = "Matthias";
        System.out.println(spelerRepo.geefAangekochteKaarten(naam));
        spelerRepo.koopKaart(naam, new Kaart(6, '-', 5));
        System.out.println(spelerRepo.geefAangekochteKaarten(naam));
    }
}
