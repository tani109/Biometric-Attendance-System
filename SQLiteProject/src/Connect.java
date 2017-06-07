/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Tanjila Mawla Tania
 */
public class Connect {
    
    Connection conn = null;
    
     public static void main( String args[] ){
    
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:attendance.sqlite");
            //JOptionPane.showMessageDialog(null,"connect");
            System.out.println("CONNECT");
            
            //return conn;
        } catch (Exception e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null,e);
        }
            //return null;
    }
    
}
