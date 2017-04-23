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
public class CardNotInGameDeckException extends IllegalArgumentException{
    public CardNotInGameDeckException(){
        super();
    }
    
    public CardNotInGameDeckException(String s){
        super(s);
    }
    public CardNotInGameDeckException(Throwable cause) {
        super(cause);
    }
}
