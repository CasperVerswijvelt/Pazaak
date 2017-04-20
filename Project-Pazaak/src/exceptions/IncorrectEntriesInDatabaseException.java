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
public class IncorrectEntriesInDatabaseException extends DatabaseException{
    public IncorrectEntriesInDatabaseException(){
        super();
    }
    
    public IncorrectEntriesInDatabaseException(String s){
        super(s);
    }
    
    public IncorrectEntriesInDatabaseException(Throwable cause){
        super(cause);
    }
}
