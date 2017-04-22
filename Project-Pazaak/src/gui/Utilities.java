/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Casper
 */
public class Utilities {

    public static String[] veranderNaarMooieLayout(String[] kaart) {
        String[] returnKaart = new String[3];
        System.arraycopy(kaart, 0, returnKaart, 0, 3);
        if (returnKaart[0].equals("W")) {
            returnKaart[0] = "";
            returnKaart[1] = kaart[1].equals("1") ? "2&4" : "3&6";
        } else if (kaart[0].equals("C")) {
            returnKaart[0] = "x+/-y";
            returnKaart[1] = "";
        } else if (kaart[0].equals("T")) {
            returnKaart[1] = "";
        } else if (kaart[0].equals("D")) {
            returnKaart[1] = "";
        } else if (kaart[0].equals("*")) {
            returnKaart[0] = "+/-";
        }
        return returnKaart;
    }
    
    public static String[] veranderNaarGewoonKaartFormaat(String[] kaart) {
        String[] returnKaart = new String[3];
        System.arraycopy(kaart, 0, returnKaart, 0, 3);
        if (returnKaart[1].contains("&")) {
            returnKaart[0] = "W";
            returnKaart[1] = "2&4".equals(kaart[1])?"1":"2";
        } else if (kaart[0].equals("x+/-y") || kaart[0].equals("C")) {
            returnKaart[0] = "C";
            returnKaart[1] = "1";
        } else if (kaart[0].equals("T")) {
            returnKaart[1] = "1";
        } else if (kaart[0].equals("D")) {
            returnKaart[1] = "0";
        } else if (kaart[0].equals("+/-")) {
            returnKaart[0] = "*";
        }
        return returnKaart;
    }
    
    
}
