/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.RecordNotFoundException;
import MySql.MySqlConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hogil
 */
public class Doctor {
    private static PreparedStatement command;
    private static ResultSet result;
    //atributos
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    
    // getters and setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String dateBirth) {
        this.password = dateBirth;
    }
    
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
    
    //constructors
    public Doctor(){
        this.id = "";
        this.firstName = "";
        this.lastName = "";
        this.password = "";
    }
    public Doctor(String id) throws RecordNotFoundException{
        String query = "select * from Doctors where doctor_id = ?";
        try{
            command = MySqlConnection.getConnection().prepareStatement(query);
            command.setString(1, id);
            result = command.executeQuery();
            result.first();
            if (result.getRow() > 0){
                this.id = id;
                this.firstName = result.getString("doctor_first_name");
                this.lastName = result.getString("doctor_last_name");
                this.password = result.getString("doctor_password");
            }
            else
                throw new RecordNotFoundException(this.getClass().getName(), String.valueOf(id));
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public Doctor(String id, String password) throws RecordNotFoundException{
         String query = "select * from Doctors where doctor_id = ? and doctor_id = ?";
        try{
            command = MySqlConnection.getConnection().prepareStatement(query);
            command.setString(1, id);
            command.setString(2, password);
            result = command.executeQuery();
            result.first();
            if (result.getRow() > 0){
                this.id = id;
                this.firstName = result.getString("doctor_first_name");
                this.lastName = result.getString("doctor_last_name");
                this.password = result.getDate("doctor_password").toString();
            }
            else
                throw new RecordNotFoundException(this.getClass().getName(), String.valueOf(id));
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    //methods
    public ArrayList<Patient> getPatients() throws RecordNotFoundException{
         ArrayList<Patient> list = new ArrayList<>();
        String query = "select patient_id from Doctors_Patients where doctor_id = ?";
        try{
            command = MySqlConnection.getConnection().prepareStatement(query);
            command.setString(1, this.id);
            result = command.executeQuery();
            
            while(result.next()){
                String id = result.getString("patient_id");
                list.add(new Patient(id));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return list;
    }
}
