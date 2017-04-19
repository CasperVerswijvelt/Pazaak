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
public class NoWinnerException extends IllegalArgumentException{
    public NoWinnerException(){
        super();
    }
    
    public NoWinnerException(String s){
        super(s);
    }
}
