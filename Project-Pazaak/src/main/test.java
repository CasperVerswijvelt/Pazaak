/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.DomeinController;
import domein.Kaart;
import domein.SpelerRepository;
import domein.WedstrijdRepository;
import java.util.Arrays;
import persistentie.KaartMapper;
import persistentie.SpelerMapper;

/**
 *
 * @author Casper
 */
public class test {
    
    
    
    public static void main(String[] args) {
//        new KaartMapper().bouwKaartTypeDatabase();
        System.out.println(Arrays.deepToString(new DomeinController().geefWedstrijdenOverzicht()));
        new WedstrijdRepository().laadWedstrijd("Test");
    }
}
