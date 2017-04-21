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
public class PlayerBirthInvalidException extends IllegalArgumentException{
    public PlayerBirthInvalidException(){
        super();
    }
    
    public PlayerBirthInvalidException(String s){
        super(s);
    }
    
    public PlayerBirthInvalidException(Throwable cause){
        super(cause);
    }
}
