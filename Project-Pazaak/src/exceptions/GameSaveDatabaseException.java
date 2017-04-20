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
public class GameSaveDatabaseException extends DatabaseException{
    public GameSaveDatabaseException(){
        super();
    }
    
    public GameSaveDatabaseException(String s){
        super(s);
    }
    
    public GameSaveDatabaseException(Throwable cause){
        super(cause);
    }
}
