/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoserver;

import Exceptions.RecordNotFoundException;
import Frames.DoctorFrame;
import Models.Doctor;

/**
 *
 * @author hogil
 */
public class ProyectoServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            new DoctorFrame(new Doctor("0314106080")).show();
        }
        catch(RecordNotFoundException ex){
            
        }
    }
    
}
