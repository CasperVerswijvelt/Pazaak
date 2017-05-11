/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cui;

import domein.DomeinController;
import exceptions.*;
import java.util.ResourceBundle;
import java.util.Scanner;
import static cui.Console.printLijn;

/**
 *
 * @author Casper
 */
public class UC8 {

    private final ResourceBundle r;
    private final DomeinController dc;
    
    public UC8(ResourceBundle r, DomeinController dc) {
        this.r =r;
        this.dc = dc;
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        printLijn();
        boolean valideKeuze = false;
        String keuze;
        do {
            try {
                System.out.print(r.getString("NAME") + ": ");
                keuze = in.nextLine().trim();
                if (keuze == null || keuze.isEmpty()) {
                    throw new IllegalArgumentException();
                }
                dc.slaWedstrijdOp(keuze);
                printLijn();
                System.out.println(r.getString("GAMESAVED"));
                printLijn();
                valideKeuze = true;

            }  catch (GameAlreadyExistsException e){
                System.out.println(r.getString("GAMEALREADYEXISTS"));
            } catch(DatabaseException e){
                System.out.println(r.getString("DATABASEERROR"));
                break;
            }catch (IllegalArgumentException e) {
                System.out.println(r.getString("INVALIDCHOICE"));
            }
            
        } while (!valideKeuze);
    }
}
