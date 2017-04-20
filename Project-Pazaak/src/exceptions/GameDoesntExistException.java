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
public class GameDoesntExistException extends IllegalArgumentException{
    public GameDoesntExistException(){
        super();
    }
    
    public GameDoesntExistException(String s){
        super(s);
    }
}
