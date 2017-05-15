/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package exceptions;

/**
 *
 * @author Casper
 */
public class GameNameTooLongException extends IllegalArgumentException{
    public GameNameTooLongException(){
        super();
    }
    
    public GameNameTooLongException(String s){
        super(s);
    }
}
