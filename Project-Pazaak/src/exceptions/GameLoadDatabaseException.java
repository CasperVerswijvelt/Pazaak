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
public class GameLoadDatabaseException extends DatabaseException{
    public GameLoadDatabaseException(){
        super();
    }
    
    public GameLoadDatabaseException(String s){
        super(s);
    }
    
    public GameLoadDatabaseException(Throwable cause){
        super(cause);
    }
}
