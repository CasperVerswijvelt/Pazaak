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
public class NoPlayersAvailableException extends IllegalArgumentException{
    public NoPlayersAvailableException(){
        super();
    }
    public NoPlayersAvailableException(String s) {
        super(s);
    }
    
}
