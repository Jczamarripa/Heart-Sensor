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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
 
/**
 *
 * @author hogil
 */
public class DoctorFrame {
    //controls
    private final JFrame frame = new JFrame("Asistente Cardio Vascular");
    private final JPanel panelAccount = new JPanel();
    private final JPanel panelButtons = new JPanel();
    private final JLabel lblDoctor = new JLabel("Doctor : ");
    private final JLabel lblPatients = new JLabel("Patients : ");
    private final JTextField txtDoctor = new JTextField(30);
    private final JButton btnCancel = new JButton("", new ImageIcon("Images/close.png"));
    
    //table 
    private final JPanel panelPatients = new JPanel();
    private String[] columnHeaders = {"ID", "First Name", "Last Name", "Date Of Birth"};
    private final JTable tablePatients = new JTable();
    private final JScrollPane scrollPatients = new JScrollPane(tablePatients);
    private DefaultTableModel modelPatients = new DefaultTableModel(0,0);
    
    public DoctorFrame(Doctor d){
        this.frame.setSize(820,600);
        this.frame.setMinimumSize(new Dimension(820,400));
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        Container container = this.frame.getContentPane();
        
        container.setLayout(new BorderLayout());
        this.panelAccount.setLayout(new GridBagLayout());
        this.panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.panelPatients.setLayout(new BorderLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);c.weightx = .5; c.anchor = GridBagConstraints.WEST;
        c.gridy = 0; c.gridx = 0; this.panelAccount.add(this.lblDoctor, c);
        c.gridy = 0; c.gridx = 1; this.panelAccount.add(this.txtDoctor, c);
        c.gridy = 1; c.gridx = 0; this.panelAccount.add(this.lblPatients, c);
        container.add(panelAccount, BorderLayout.NORTH);
        
        this.tablePatients.setFillsViewportHeight(true);
        this.tablePatients.setModel(this.modelPatients);
        this.modelPatients.setColumnIdentifiers(this.columnHeaders);
        
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tablePatients.setRowHeight(25);
        panelPatients.add(scrollPatients, BorderLayout.CENTER);
        container.add(panelPatients, BorderLayout.CENTER);
        
        this.btnCancel.setHorizontalTextPosition(SwingConstants.CENTER);
        this.btnCancel.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.panelButtons.add(this.btnCancel);
        container.add(this.panelButtons, BorderLayout.SOUTH);
        
        this.btnCancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
            
        });
        
        this.tablePatients.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent evt){
                if(evt.getClickCount() == 2 && !evt.isConsumed()){
                    evt.consume();
                    int row = tablePatients.rowAtPoint(evt.getPoint());
                    int col = tablePatients.columnAtPoint(evt.getPoint());
                    if(row >= 0 && col == 0){
                        try{
                             new PatientFrame(new Patient(tablePatients.getModel().getValueAt(row, col).toString())).show();
                        }
                        catch(RecordNotFoundException ex){
                            JOptionPane.showMessageDialog(frame,"Account Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DoctorFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
               
            }
        });
        
        this.txtDoctor.setText(d.getFullName());
        this.txtDoctor.setEditable(false);
        
        this.tablePatients.setDefaultEditor(Object.class, null);
        
        try{
           showPatients(d);
        }
        catch(RecordNotFoundException ex){
            JOptionPane.showMessageDialog(this.frame,"Account Not Found ", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void show(){
        this.frame.setVisible(true);
    }
    
    private void close(){
        if(JOptionPane.showConfirmDialog(this.frame, "Exit Application", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
    
    private void showPatients(Doctor d) throws RecordNotFoundException{
        try{
            for(Patient p : d.getPatients() ){
                this.modelPatients.addRow(new Object[] {p.getId(), p.getFirstName(), p.getLastName(), p.getDateBirth()});            
            }
        }
        catch(RecordNotFoundException ex){
            JOptionPane.showMessageDialog(this.frame,"Account Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }  
}
