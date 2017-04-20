/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Casper
 */
public class PlayerNameInvalidException extends IllegalArgumentException{
    public PlayerNameInvalidException(){
        super();
    }
    
    public PlayerNameInvalidException(String s){
        super(s);
    }
    
    public PlayerNameInvalidException(Throwable cause){
        super(cause);
    }
}
