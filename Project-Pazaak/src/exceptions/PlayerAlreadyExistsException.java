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
public class PlayerAlreadyExistsException extends PlayerException{
    public PlayerAlreadyExistsException(){
        super();
    }
    
    public PlayerAlreadyExistsException(String s){
        super(s);
    }
    public PlayerAlreadyExistsException(Throwable cause){
        super(cause);
    }
}
