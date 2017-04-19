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
public class GameAlreadyExistsException extends IllegalArgumentException{
    public GameAlreadyExistsException(){
        super();
    }
    
    public GameAlreadyExistsException(String s){
        super(s);
    }
}
