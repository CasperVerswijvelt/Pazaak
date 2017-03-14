/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author goran
 */
public class Console {

    private DomeinController dc;
    private ResourceBundle r;
    private Locale l = new Locale("nl", "BE");
    
    Console(DomeinController dc) {
        this.dc = dc;
    }
    
    public void start() {
        new UC2().start();
        System.out.println(r.getString("WELCOME"));
        switch (menu() ){
            case 1: 
                dc.maakNieuweSpelerAan("Goran", 1990);
                
        }
}
    private int menu() {
        Scanner invoer = new Scanner(System.in);
        int keuze;
        System.out.println("Om een nieuwe speler aan te maken duw 1");
        keuze = invoer.nextInt();
        return keuze;
    }
}
