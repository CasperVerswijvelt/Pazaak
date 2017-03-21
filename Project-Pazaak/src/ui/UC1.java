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
/**
 *
 * @author Bruno
 */
public class UC1 {
    //attributen
    private String naam;
    private int gebJaar;
    private final DomeinController dc;
    private final ResourceBundle r;
    
    public UC1(DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
    }

    public void start() {
        Scanner input = new Scanner(System.in);
        boolean opnieuw = true;
        do {
            try {
                System.out.print(r.getString("NEWPLAYERNAME"));
                naam = input.nextLine();

                System.out.print(r.getString("NEWPLAYERYEAR"));
                gebJaar = Integer.parseInt(input.nextLine());

                dc.maakNieuweSpelerAan(naam, gebJaar);
                opnieuw = false;
            } catch (PlayerAlreadyExistsException e) {
                System.out.println(r.getString("PLAYERALREADYEXISTS"));
            } catch(IllegalArgumentException e){
                System.out.println(r.getString("NAMEREQUIREMENTS"));
            }
        } while (opnieuw);

    }

}
