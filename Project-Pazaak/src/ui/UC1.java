/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author Bruno
 */
public class UC1 {

    //attributen
    private String naam;
    private int gebJaar;

    public void start(ResourceBundle r) {
        Scanner input = new Scanner(System.in);
        DomeinController dc = new DomeinController();

        System.out.print(r.getString("NEWPLAYERNAME"));
       
    }

}
