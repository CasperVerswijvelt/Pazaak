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
public class InsufficientBalanceException extends IllegalArgumentException{
    public InsufficientBalanceException(){
        super();
    }
    
    public InsufficientBalanceException(String s){
        super(s);
    }
}
