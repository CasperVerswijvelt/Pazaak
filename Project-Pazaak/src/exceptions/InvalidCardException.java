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
public class InvalidCardException extends CardException{
    public InvalidCardException(){
        super();
    }
    
    public InvalidCardException(String s){
        super(s);
    }
    
    public InvalidCardException(Throwable cause) {
        super(cause);
    }
}
