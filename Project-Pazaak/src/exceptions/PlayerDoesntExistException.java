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
public class PlayerDoesntExistException extends PlayerException{
    public PlayerDoesntExistException(){
        super();
    }
    
    public PlayerDoesntExistException(String s){
        super(s);
    }
    
    public PlayerDoesntExistException(Throwable cause){
        super(cause);
    }
}
