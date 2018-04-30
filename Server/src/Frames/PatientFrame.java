/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import Exceptions.RecordNotFoundException;
import Models.Doctor;
import Models.HearthRate;
import Models.Patient;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;

/**
 *
 * @author hogil
 */


public class PatientFrame {
    //socket
    
    static ServerSocket ss;
    static Socket s;
    static InputStreamReader isr;
    static BufferedReader br;
    static PrintStream ps;
    
    
    //objects
    //controls
    private final JFrame frame = new JFrame("======= Hearth Rates ===");
    private final JPanel panelAccount = new JPanel();
    private final JPanel panelButtons = new JPanel();
    private final JLabel lblPatient = new JLabel("Patient : ");
    private final JLabel lblHearthRates = new JLabel("Hearth Rates : ");
    private final JTextField txtPatient = new JTextField(30);
    private final JButton btnCancel = new JButton("", new ImageIcon("Images/close.png"));
    
    //table 
    private final JPanel panelHearthRates = new JPanel();
    private String[] columnHeaders = {"Patient", "Pulse", "Date"};
    private final JTable tableHearthRates = new JTable();
    private final JScrollPane scrollHearthRates = new JScrollPane(tableHearthRates);
    private DefaultTableModel modelHearthRates = new DefaultTableModel(0,0);
    
    public PatientFrame(Patient p) throws InterruptedException{
        this.frame.setSize(820,600);
        this.frame.setMinimumSize(new Dimension(820,400));
        this.frame.setLocationRelativeTo(null);
        
        Container container = this.frame.getContentPane();
        
        container.setLayout(new BorderLayout());
        this.panelAccount.setLayout(new GridBagLayout());
        this.panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.panelHearthRates.setLayout(new BorderLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);c.weightx = .5; c.anchor = GridBagConstraints.WEST;
        c.gridy = 0; c.gridx = 0; this.panelAccount.add(this.lblPatient, c);
        c.gridy = 0; c.gridx = 1; this.panelAccount.add(this.txtPatient, c);
        c.gridy = 1; c.gridx = 0; this.panelAccount.add(this.lblHearthRates, c);
        container.add(panelAccount, BorderLayout.NORTH);
        
        this.tableHearthRates.setFillsViewportHeight(true);
        this.tableHearthRates.setModel(this.modelHearthRates);
        this.modelHearthRates.setColumnIdentifiers(this.columnHeaders);
        
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tableHearthRates.getColumnModel().getColumn(1).setCellRenderer(rightAlign);
        this.tableHearthRates.getColumnModel().getColumn(2).setCellRenderer(rightAlign);
        this.tableHearthRates.setRowHeight(25);
        panelHearthRates.add(scrollHearthRates, BorderLayout.CENTER);
        container.add(panelHearthRates, BorderLayout.CENTER);
        
        this.btnCancel.setHorizontalTextPosition(SwingConstants.CENTER);
        this.btnCancel.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.panelButtons.add(this.btnCancel);
        container.add(this.panelButtons, BorderLayout.SOUTH);
        
        
        
       
        
        this.txtPatient.setText(p.getFullName());
        this.txtPatient.setEditable(false);
        
        this.tableHearthRates.setDefaultEditor(Object.class, null);
        
        try{
            showHearthRates(p);
        }
        catch(RecordNotFoundException ex){
            JOptionPane.showMessageDialog(this.frame,"Account Not Found -*---", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////
        //json
        final String STATUS = "status";
        final String HEARTHRATE = "hearthRate";
        final String TIMESTAMP = "timestamp";
         //thread
        Thread socketThread = new Thread(new Runnable(){
        @Override
            public void run(){
                try{
                    System.out.println("Openning Socket...");//status
                    ss = new ServerSocket(12000); // socket port
                    System.out.println("Socked opened...");// status again
                    //keep reading
                    while(true){
                        s = ss.accept(); //read data
                        isr = new InputStreamReader(s.getInputStream());
                        br = new BufferedReader(isr);// reader of buffer
                        String data = br.readLine();
                        JSONObject json = new JSONObject(data);
                        int status = json.getInt(STATUS);
                        if(status == 0){
                            double hearthRate = json.getDouble(HEARTHRATE);
                            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(json.getString(TIMESTAMP));
                            HearthRate hr = new HearthRate(p, hearthRate);
                            if(hr.add()){
                                System.out.println(data + "\n");
                                modelHearthRates.insertRow(0,new Object[] {hr.getPatient().getFullName(), hr.getPulse(), date});
                            }
                            else{
                                String message = "{" + "\"status\":" + 1 + ","+ 
                                    "\"message\":\"Error\"" +
                                    "}";
                            }
                        }
                        else{
                             System.out.println(data + "\n");//show data
                        }
                        //send a knowledge to client
                        if (data != null){
                            ps = new PrintStream(s.getOutputStream());
                            ps.println("Data recived...");
                        }
                        
                    }
                    
                }
                catch(IOException ex)
                {
                    System.out.println(ex.getMessage());
                } 
                catch (ParseException ex) {
                    Logger.getLogger(PatientFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        //start socketd
        socketThread.start();
        
        this.btnCancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
                try {
                    ss.close();
                } catch (IOException ex) {
                    Logger.getLogger(PatientFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    
        //////////////////////////////////////////////////////////////////////////////////////////
    }
    
    public void show(){
        this.frame.setVisible(true);
    }
    
    private void close(){
        this.frame.setVisible(false);
        
    }
    
    private void showHearthRates(Patient p) throws RecordNotFoundException{
        try{
            for(HearthRate hr : p.getHearthRates()){
                this.modelHearthRates.addRow(new Object[] {hr.getPatient().getFullName(), hr.getPulse(), hr.getDate()});            
            }
        }
        catch(RecordNotFoundException ex){
            JOptionPane.showMessageDialog(this.frame,"Account Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
