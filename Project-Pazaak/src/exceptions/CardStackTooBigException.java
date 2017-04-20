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
public class CardStackTooBigException extends CardException{
    public CardStackTooBigException(){
        super();
    }
    public CardStackTooBigException(String s){
        super(s);
    }
    public CardStackTooBigException(Throwable cause) {
        super(cause);
    }
}
