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
public class PlayerException extends IllegalArgumentException{
    public PlayerException(){
        super();
    }
    
    public PlayerException(String s){
        super(s);
    }
    
    public PlayerException(Throwable cause){
        super(cause);
    }
}
