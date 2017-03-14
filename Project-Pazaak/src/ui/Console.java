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
    
    public Console(DomeinController dc) {
        this.dc = dc;
    }
    
    public void start() {
        UC2 uc2 = new UC2();
        uc2.start();
        r= uc2.getResourceBundle();
        System.out.println(r.getString("WELCOME"));
        switch (menu() ){
            case 1: 
                dc.maakNieuweSpelerAan("Goran", 1990);
                
        }
}
    private int menu() {
        Scanner invoer = new Scanner(System.in);
        int keuze;
        System.out.println(r.getString("NEWPLAYERPRESS1"));
        keuze = invoer.nextInt();
        return keuze;
    }
}
