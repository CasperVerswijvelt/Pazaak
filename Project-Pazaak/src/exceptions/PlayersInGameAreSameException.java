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
public class PlayersInGameAreSameException extends IllegalArgumentException{
    public PlayersInGameAreSameException(){
        super();
    }
    
    public PlayersInGameAreSameException(String s){
        super(s);
    }
    public PlayersInGameAreSameException(Throwable cause){
        super(cause);
    }
}
