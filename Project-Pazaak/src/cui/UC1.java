/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cui;

import domein.DomeinController;
import java.util.ResourceBundle;
import java.util.Scanner;
import exceptions.*;
import static cui.Console.*;
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
        
        System.out.println(r.getString("NEWPLAYEROPTION"));
        printLijn();
        boolean opnieuw = true;
        do {
            try {
                System.out.print(r.getString("NEWPLAYERNAME"));
                naam = input.nextLine().trim();

                System.out.print(r.getString("NEWPLAYERYEAR"));
                String gebJaarInput = input.nextLine().trim();
                
                if(naam.isEmpty() || gebJaarInput.isEmpty())
                    throw new IllegalArgumentException();
                
                
                int gebJaar = Integer.parseInt(gebJaarInput);

                dc.maakNieuweSpelerAan(naam, gebJaar);
                opnieuw = false;
                System.out.println(r.getString("PLAYERADDED"));
                printLijn();
            } catch (PlayerAlreadyExistsException e) {
                printLijn();
                System.out.println(r.getString("PLAYERALREADYEXISTS"));
                return;
            } catch(PlayerNameInvalidException e){
                printLijn();
                System.out.println(r.getString("NAMEREQUIREMENTS"));
                return;
            }catch(PlayerBirthInvalidException | NumberFormatException e) {
                printLijn();
                System.out.println(r.getString("BIRTHREQUIREMENTS"));
                return;
            } catch(DatabaseException e) {
                printLijn();
                System.out.println(r.getString("DATABASEERROR"));
                return;
            } catch(IllegalArgumentException e) {
                printLijn();
                System.out.println(r.getString("FILLINALLFIELDS"));
                return;
            }
        //Zolang opgegeven info niet aan vereisten voldoet wordt deze opnieuw opgevraagd
        } while (opnieuw);
        String[] info = dc.geefSpelerInfo(naam);
        
        System.out.printf("%s: %s %n%s: %s %n%s: %s %n%s: ", r.getString("NAME"),info[0], r.getString("CREDITS"), info[1], r.getString("BIRTH"), info[2], r.getString("CARDS"));
        System.out.println(formatteerStapelOpLijn(dc.geefStartStapel(naam), false));;

    }

}
