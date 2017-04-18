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
public class CardDoesntExistException extends IllegalArgumentException{
    public CardDoesntExistException(){
        super();
    }
    
    public CardDoesntExistException(String s){
        super(s);
    }
}
