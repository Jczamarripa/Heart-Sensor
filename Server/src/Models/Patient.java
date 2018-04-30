/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.RecordNotFoundException;
import MySql.MySqlConnection;
import static java.lang.Compiler.command;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hogil
 */
public class Patient {
    private static PreparedStatement command;
    private static ResultSet result;
    //atributos
    private String id;
    private String firstName;
    private String lastName;
    private String dateBirth;
    
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
    public String getDateBirth() {
        return dateBirth;
    }
    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }
    
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
    //constructors
    public Patient(){
        this.id = "";
        this.firstName = "";
        this.lastName = "";
        this.dateBirth = "";
    }
    public Patient(String id) throws RecordNotFoundException{
        String query = "select * from Patients where patient_id = ?";
        try{
            command = MySqlConnection.getConnection().prepareStatement(query);
            command.setString(1, id);
            result = command.executeQuery();
            result.first();
            if (result.getRow() > 0){
                this.id = id;
                this.firstName = result.getString("patient_first_name");
                this.lastName = result.getString("patient_last_name");
                this.dateBirth = result.getDate("patient_date_birth").toString();
            }
            else
                throw new RecordNotFoundException(this.getClass().getName(), String.valueOf(id));
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    
    //methods
    public ArrayList<HearthRate> getHearthRates() throws RecordNotFoundException{
        ArrayList<HearthRate> listHr = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        String query = "select hr_id from HearthRates where hr_patient_id = ? order by hr_date desc";
        try{
            command = MySqlConnection.getConnection().prepareStatement(query);
            command.setString(1, this.id);
            result = command.executeQuery();
            
            while(result.next()){
                int hr_id = result.getInt("hr_id");
                ids.add(hr_id);
            }
            
            for(int i : ids){
                listHr.add(new HearthRate(i));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return listHr;
    }
    
}
