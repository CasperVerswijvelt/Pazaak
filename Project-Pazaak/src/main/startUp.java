package main;

import domein.DomeinController;
import ui.Console;

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
        new Console(new DomeinController()).start();

    }
}
