/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;


public class RecordNotFoundException extends Exception{
    //attributes
    private String message;
    
    //getters and setters
    @Override
    public String getMessage(){
        return this.message;
    }
    
    //constructor
    public RecordNotFoundException(String className, String id){
        String parts[] = className.split("\\.");
        this.message = parts[parts.length - 1] + " " + id + " Not Found";
    }
}
