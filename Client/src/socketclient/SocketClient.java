/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

//arduino
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author hogil
 */
public class SocketClient {

    //objects
    static Socket s;
    static PrintStream ps;
    static InputStreamReader isr;
    static BufferedReader br;
    static String message, response;
    
    public static void main(String[] args) {
        
        //simulating data
        String station = "1";
        
        //Arduino
            PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
            SerialPortEventListener listener = new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent spe) {
                String unixDateString = "";
            
                try {
                    if(ino.isMessageAvailable()){
                        
                        String messageA = ino.printMessage();
                        double pulse = Double.parseDouble(messageA);
                        s = new Socket("localhost",12000);//create socket
                        ps = new PrintStream(s.getOutputStream()); //output string
                        isr = new InputStreamReader(s.getInputStream()); //stream reader
                        br = new BufferedReader(isr); //bufer reader
                        
                       unixDateString = String.valueOf(System.currentTimeMillis() / 1000);
                        message = "{" + "\"status\":" + 0 + ","+
                        "\"hearthRate\":" + new DecimalFormat("#.00").format(pulse) + "," +
                        "\"timestamp\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+ "\"" +
                        "}";
                        System.out.println(message);
                        ps.println(message);//send mesage
                        //acknowledgment from server
                        response = br.readLine();
                        System.out.println(response);
                        s.close();//close socket
                    }
                } catch (SerialPortException ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ArduinoException ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        };
            
            try {
                ino.arduinoRX("/dev/tty.usbmodem1411", 9600, listener); //primer parametro es el puerto com
            } catch (ArduinoException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
    //End
    
    
    /*
    //old socket
        Random r = new Random(); //RNG
        try{
            for(int i = 1; i <= 10; i++){// send 10 readings
                double pulse = Double.parseDouble(message);
                s = new Socket("localhost",12000);//create socket
                ps = new PrintStream(s.getOutputStream()); //output string
                isr = new InputStreamReader(s.getInputStream()); //stream reader
                br = new BufferedReader(isr); //bufer reader
//bufer reader
                
                //message for server
                unixDateString = String.valueOf(System.currentTimeMillis() / 1000);
                message = "{" + "\"status\":" + 0 + ","+
                        "\"hearthRate\":" + new DecimalFormat("#.00").format(pulse) + "," +
                        "\"timestamp\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+ "\"" +
                        "}";
                System.out.println(message);
                ps.println(message);//send mesage
                //acknowledgment from server
                response = br.readLine();
                System.out.println(response);
                s.close();//close socket
                //wait one second
                try{
                    Thread.sleep(1000);//milliseconds
                }
                catch(InterruptedException e){}
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        
        //old socket end
        */
    }
    
}
