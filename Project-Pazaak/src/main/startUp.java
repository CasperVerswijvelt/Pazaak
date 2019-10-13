package main;

import domein.*;
import cui.Console;

import java.util.Scanner;

import static cui.Console.printLijn;

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

        Scanner in = new Scanner(System.in);

        int choice = 0;
        while(choice == 0) {
            printLijn();
            System.out.println("Would you like to use Pazaak in online or offline mode?");
            printLijn();
            System.out.print(" 1. Online\n 2. Offline\nChoice: ");

            try {
                choice = Integer.parseInt(in.nextLine());
            } catch (Exception e){
            }
            if(choice < 1 || choice > 2) {
                choice = 0;
                printLijn();
                System.out.println("Invalid choice");
            }
        }

        printLijn();
        (choice == 1 ? new Console(new DomeinController(new SpelerEnKaartRepository(), new WedstrijdRepository())) :
                       new Console(new DomeinController(new OfflineSpelerEnKaartRepository(), new OfflineWedstrijdRepository()))).start();
    }
}
