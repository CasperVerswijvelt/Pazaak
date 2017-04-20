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
public class CardException extends IllegalArgumentException{
    public CardException(){
        super();
    }
    
    public CardException(String s){
        super(s);
    }
    public CardException(Throwable cause) {
        super(cause);
    }
}
