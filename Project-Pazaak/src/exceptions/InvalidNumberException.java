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
public class InvalidNumberException extends Exception{
    public InvalidNumberException(){
        super();
    }
    public InvalidNumberException(String s){
        super(s);
    }
}
