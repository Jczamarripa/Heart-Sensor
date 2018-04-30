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

/**
 *
 * @author hogil
 */
public class HearthRate {
    private static PreparedStatement command;
    private static ResultSet result;
    
    //atributos
    private int id;
    private Patient patient;
    private double pulse;
    private String date;
    //getters and setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public double getPulse() {
        return pulse;
    }
    public void setPulse(double pulse) {
        this.pulse = pulse;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
    public HearthRate(){
        this.id = 0;
        this.patient = new Patient();
        this.pulse = 0;
        this.date = "";
    }
    public HearthRate(Patient patient, double pulse){
        this.patient = patient;
        this.pulse = pulse;
    }
    public HearthRate(int id) throws RecordNotFoundException{
        String query = "select * from HearthRates where hr_id = ?";
        try{
            command = MySqlConnection.getConnection().prepareStatement(query);
            command.setInt(1, id);
            result = command.executeQuery();
            result.first();
            if (result.getRow() > 0){
                this.id = id;
                this.patient = new Patient(result.getString("hr_patient_id"));
                this.pulse= result.getDouble("hr_pulse");
                this.date = result.getTimestamp("hr_date").toString();
            }
            else
                throw new RecordNotFoundException(this.getClass().getName(), String.valueOf(id));
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    //methods
    public boolean add(){
        boolean result = false;
        String insert = "insert into HearthRates values (null,?,?,null)";
        try{
            command = MySqlConnection.getConnection().prepareStatement(insert);
            command.setString(1, this.patient.getId());
            command.setDouble(2, this.pulse);
            
            if(command.executeUpdate() > 0){
                result = true;
            }
        }
       catch(SQLException ex){
           System.out.println(ex.getMessage());
       }
        return result;

    }
    
}
