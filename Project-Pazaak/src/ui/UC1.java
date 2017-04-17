/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import java.util.ResourceBundle;
import java.util.Scanner;
import exceptions.PlayerAlreadyExistsException;
import static ui.Console.*;
/**
 *
 * @author Bruno
 */
public class UC1 {
    //attributen
    private final DomeinController dc;
    private final ResourceBundle r;
    
    public UC1(DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
    }

    public void start() {
        Scanner input = new Scanner(System.in);
        
        String naam="";
        int gebJaar;
        
        boolean opnieuw = true;
        do {
            try {
                System.out.print(r.getString("NEWPLAYERNAME"));
                naam = input.nextLine();

                System.out.print(r.getString("NEWPLAYERYEAR"));
                gebJaar = Integer.parseInt(input.nextLine());

                dc.maakNieuweSpelerAan(naam, gebJaar);
                opnieuw = false;
                System.out.println(r.getString("PLAYERADDED"));
                printLijn();
            } catch (PlayerAlreadyExistsException e) {
                System.out.println(r.getString("PLAYERALREADYEXISTS"));
            } catch(IllegalArgumentException e){
                System.out.println(r.getString("NAMEREQUIREMENTS"));
            }
        //Zolang opgegeven info niet aan vereisten voldoet wordt deze opnieuw opgevraagd
        } while (opnieuw);
        String[] info = dc.geefSpelerInfo(naam);
        
        System.out.printf("%s: %s %n%s: %s %n%s: %s %n%s: ", r.getString("NAME"),info[0], r.getString("CREDITS"), info[1], r.getString("BIRTH"), info[2], r.getString("CARDS"));
        toonSpelbord(dc.geefStartStapel(naam));

    }

}
