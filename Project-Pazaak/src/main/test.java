/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.Kaart;
import domein.SpelerRepository;
import persistentie.KaartMapper;
import persistentie.SpelerMapper;

/**
 *
 * @author Casper
 */
public class test {
    
    
    
    public static void main(String[] args) {
//        new KaartMapper().bouwKaartTypeDatabase();
        SpelerRepository spelerRepo = new SpelerRepository();
        String naam = "nigel";
        System.out.println(spelerRepo.geefStartStapel(naam));
        spelerRepo.koopKaart(naam, new Kaart(6, '-', 5));
        System.out.println(spelerRepo.geefStartStapel(naam));
    }
}
