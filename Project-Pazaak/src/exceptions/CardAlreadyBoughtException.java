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
public class CardAlreadyBoughtException extends CardException{
    public CardAlreadyBoughtException(){
        super();
    }
    
    public CardAlreadyBoughtException(String s){
        super(s);
    }
    
    public CardAlreadyBoughtException(Throwable cause) {
        super(cause);
    }
}
