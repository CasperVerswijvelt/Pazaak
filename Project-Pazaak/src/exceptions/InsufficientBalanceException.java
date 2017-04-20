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
public class InsufficientBalanceException extends PlayerException{
    public InsufficientBalanceException(){
        super();
    }
    
    public InsufficientBalanceException(String s){
        super(s);
    }
    public InsufficientBalanceException(Throwable cause){
        super(cause);
    }
}
