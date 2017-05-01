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
public class InvalidAdminCredentialsException extends IllegalArgumentException{
    public InvalidAdminCredentialsException(){
        super();
    }
    
    public InvalidAdminCredentialsException(String s){
        super(s);
    }
    
    public InvalidAdminCredentialsException(Throwable cause) {
        super(cause);
    }
}
