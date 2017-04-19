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
        System.out.println(r.getString("CHOOSEWHICHPLAYERTOBUYCARDS"));
        String naam = promptSpelerUitLijst(r, spelers,r.getString("CHOICE")+": ");
        printLijn();
        System.out.println("| " + r.getString("SHOP") + " | "+r.getString("CREDITS")+": " + dc.geefSpelerInfo(naam)[1] + " | " + r.getString("BACKTOMENU"));
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
                System.out.println(formatteerKaart(kaartKeuze, false) + r.getString("BOUGHT") + kaartKeuze[2] + r.getString("FORCREDIT"));
                valideKeuze = true;
            }catch(InsufficientBalanceException e) {
                System.out.println(r.getString("INSUFFICIENTBALANCE"));
            }catch(CardDoesntExistException e) {
                System.out.println(r.getString("DOESNOTEXIST"));
            }catch(PlayerDoesntExistException e) {
                System.out.println(r.getString("INVALIDPLAYER"));
            }catch(Exception e) {
                System.out.println(r.getString("INVALIDCHOICE"));
            }
            
            
        }while(!valideKeuze);
    }
    
}
