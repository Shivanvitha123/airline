package airlinemanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class BookFlight extends JFrame implements ActionListener {
    
    JTextField tfpassport, tfsource, tfdestination;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfcode;
    JButton bookflight, fetchButton, flight;
    JDateChooser dcdate;
    
    public BookFlight() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(420, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);
        
        JLabel lblpassport = new JLabel("Passport No");
        lblpassport.setBounds(60, 80, 150, 25);
        lblpassport.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblpassport);
        
        tfpassport = new JTextField();
        tfpassport.setBounds(220, 80, 150, 25);
        add(tfpassport);
        
        fetchButton = new JButton("Fetch User");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);
        
        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        add(tfname);
        
        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);
        
        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);
        
        tfaddress = new JLabel();
        tfaddress.setBounds(220, 230, 150, 25);
        add(tfaddress);
        
        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblgender);
        
        labelgender = new JLabel();
        labelgender.setBounds(220, 280, 150, 25);
        add(labelgender);
        
        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 330, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsource);
        
        tfsource = new JTextField();
        tfsource.setBounds(220, 330, 150, 25);
        add(tfsource);
        
        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(60, 380, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldest);
        
        tfdestination = new JTextField();
        tfdestination.setBounds(220, 380, 150, 25);
        add(tfdestination);
        
        flight = new JButton("Fetch Flights");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(380, 380, 120, 25);
        flight.addActionListener(this);
        add(flight);
        
        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 430, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfcode);
        
        labelfcode = new JLabel();
        labelfcode.setBounds(220, 430, 150, 25);
        add(labelfcode);
        
        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(60, 480, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldate);
        
        dcdate = new JDateChooser();
        dcdate.setBounds(220, 480, 150, 25);
        add(dcdate);
        
        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.BLACK);
        bookflight.setForeground(Color.WHITE);
        bookflight.setBounds(220, 530, 150, 25);
        bookflight.addActionListener(this);
        add(bookflight);
        
        setSize(800, 700);
        setLocation(200, 50);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String passportno = tfpassport.getText();
            try {
                Conn conn = new Conn();
                String query = "select * from passenger where passportno = '" + passportno + "'";
                ResultSet rs = conn.s.executeQuery(query);
                if (rs.next()) {
                    tfname.setText(rs.getString("name")); 
                    tfnationality.setText(rs.getString("nationality")); 
                    tfaddress.setText(rs.getString("address"));
                    labelgender.setText(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Passport Number");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            Random random = new Random();
            boolean noFlight = random.nextBoolean();
            if (noFlight) {
                labelfcode.setText("No Flights Found");
            } else {
                labelfcode.setText(String.valueOf(10000 + random.nextInt(90000)));
            }
        } else if (ae.getSource() == bookflight) {
            String passportno = tfpassport.getText();
            String name = tfname.getText();
            String nationality = tfnationality.getText();
            String src = tfsource.getText();
            String dest = tfdestination.getText();
            String flightcode = labelfcode.getText();
            String ddate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();

            if (flightcode.equals("No Flights Found")) {
                JOptionPane.showMessageDialog(null, "Cannot book a flight. No flights available.");
                return;
            }

            try {
                Conn conn = new Conn();
                Random random = new Random();
                String query = "insert into reservation values('PNR-" + (100000 + random.nextInt(900000)) + "', 'TIC-" + (10000 + random.nextInt(90000)) + "', '" + passportno + "', '" + name + "', '" + nationality + "', '" + src + "', '" + dest + "', '" + flightcode + "', '" + ddate + "')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Ticket Booked Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BookFlight();
    }
}
