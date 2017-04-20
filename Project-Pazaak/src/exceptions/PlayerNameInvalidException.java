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
public class PlayerNameInvalidException extends PlayerException{
    public PlayerNameInvalidException(){
        super();
    }
    
    public PlayerNameInvalidException(String s){
        super(s);
    }
    
    public PlayerNameInvalidException(Throwable cause){
        super(cause);
    }
}
