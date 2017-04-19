/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import exceptions.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import static ui.Console.*;

/**
 *
 * @author Casper
 */
public class UC7 {

    private DomeinController dc;
    private ResourceBundle r;
    
    public UC7(DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r =r;
    }


    void start() {
        Scanner in = new Scanner(System.in);
        List<String> spelers = dc.geefAlleSpelerNamen();
        System.out.println("CHOOSEWHICHPLAYERTOBUYCARDS");
        String naam = promptSpelerUitLijst(r, spelers,r.getString("CHOICE")+": ");
        printLijn();
        System.out.println("| " + "SHOP" + " | "+r.getString("CREDITS")+": " + dc.geefSpelerInfo(naam)[1] + " | " + " (0 TO GO BACK TO MENU)");
        printLijn();
        String[][] nietGekochteKaarten = dc.geefNogNietGekochteKaarten(naam);
        System.out.println(formatteerStapelAlsLijst(nietGekochteKaarten, true));
        
        boolean valideKeuze = false;
        int keuze = 0;
        do{
            try{
                System.out.print(r.getString("CHOICE")+": ");
                keuze = Integer.parseInt(in.nextLine());
                if(keuze>nietGekochteKaarten.length || keuze <0){
                    throw new InvalidNumberException();
                }
                if(keuze == 0)
                    break;
                String[] kaartKeuze = nietGekochteKaarten[keuze-1];
                dc.koopKaart(naam, kaartKeuze);
                System.out.println(formatteerKaart(kaartKeuze, false) + " SUCCESFULLY BOUGHT FOR " + kaartKeuze[2] + " CREDITS");
                valideKeuze = true;
            }catch(InsufficientBalanceException e) {
                System.out.println("INSUFFICIENT BALANCE");
            }catch(CardDoesntExistException e) {
                System.out.println("CARD DOES NOT EXIST IN DATABASE");
            }catch(PlayerDoesntExistException e) {
                System.out.println("PLAYER DOESN'T EXIST");
            }catch(Exception e) {
                System.out.println(r.getString("INVALIDCHOICE"));
            }
            
            
        }while(!valideKeuze);
    }
    
}
