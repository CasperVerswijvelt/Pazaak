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
public class DatabaseException extends IllegalArgumentException{
    public DatabaseException(){
        super();
    }
    
    public DatabaseException(String s){
        super(s);
    }
    
    public DatabaseException(Throwable cause){
        super(cause);
    }
}
