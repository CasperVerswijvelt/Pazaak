package main;

import domein.DomeinController;
import domein.SpelerEnKaartRepository;
import persistentie.KaartMapper;
import cui.Console;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.1

 */
/**
 *
 * @author Casper
 */
public class startUp {

    public static void main(String[] args) {
        new Console(new DomeinController()).start();
    }
}
